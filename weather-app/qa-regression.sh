#!/bin/bash
# QA Regression - Generic + Solo --auto + Branching
# Usage:
#   ./qa-regression.sh --auto              # solo dev: stash -> test before -> pop -> test after
#   ./qa-regression.sh develop feat/x      # team: branch before vs after
#   ./qa-regression.sh dirBefore dirAfter  # generic dirs

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

summary() {
  local dir=$1
  if [ ! -d "$dir" ]; then
    echo "0 total, 0 failed, 0 passed (dir not found: $dir)"
    return
  fi
  local total=$(find "$dir" -name "*.xml" -exec grep -h "testsuite" {} + 2>/dev/null | grep -o 'tests="[0-9]*"' | grep -o '[0-9]*' | awk '{s+=$1} END {print s+0}')
  local failed=$(find "$dir" -name "*.xml" -exec grep -h "testsuite" {} + 2>/dev/null | grep -o 'failures="[0-9]*"' | grep -o '[0-9]*' | awk '{s+=$1} END {print s+0}')
  local errors=$(find "$dir" -name "*.xml" -exec grep -h "testsuite" {} + 2>/dev/null | grep -o 'errors="[0-9]*"' | grep -o '[0-9]*' | awk '{s+=$1} END {print s+0}')
  local failedTotal=$((failed + errors))
  echo "$total total, $failedTotal failed, $((total - failedTotal)) passed"
}

# Mode --auto for solo dev
if [ "$1" = "--auto" ]; then
  echo "🔄 Solo --auto: stash -> test Before -> restore -> test After"
  BEFORE_TMP=$(mktemp -d)
  AFTER_TMP=$(mktemp -d)
  echo "Stashing current changes for BEFORE..."
  git stash push -m "qa-before-tmp" --keep-index --quiet || true
  echo "Running test for BEFORE (stashed)..."
  ./gradlew test --rerun-tasks -q 2>&1 | tail -5 || true
  cp -r build/test-results/test "$BEFORE_TMP"/ 2>/dev/null || cp -r build/test-results "$BEFORE_TMP"/ 2>/dev/null || true
  git stash pop --quiet || true
  echo "Running test for AFTER (current)..."
  ./gradlew test --rerun-tasks -q 2>&1 | tail -5 || true
  cp -r build/test-results/test "$AFTER_TMP"/ 2>/dev/null || cp -r build/test-results "$AFTER_TMP"/ 2>/dev/null || true
  echo ""
  echo "Generating report..."
  # Recursively call self with dirs
  exec "$0" "$BEFORE_TMP" "$AFTER_TMP"
fi

# Mode 2 branches: e.g., ./qa-regression.sh develop feat/air-quality
if [ $# -eq 2 ] && git rev-parse --verify "$1" >/dev/null 2>&1 && git rev-parse --verify "$2" >/dev/null 2>&1; then
  echo "🌿 Branch mode: $1 (BEFORE) vs $2 (AFTER)"
  BEFORE_TMP=$(mktemp -d)
  AFTER_TMP=$(mktemp -d)
  git checkout "$1" --quiet
  ./gradlew test --rerun-tasks -q 2>&1 | tail -3 || true
  cp -r build/test-results/test "$BEFORE_TMP"/ 2>/dev/null || cp -r build/test-results "$BEFORE_TMP"/ 2>/dev/null || true
  git checkout "$2" --quiet
  ./gradlew test --rerun-tasks -q 2>&1 | tail -3 || true
  cp -r build/test-results/test "$AFTER_TMP"/ 2>/dev/null || cp -r build/test-results "$AFTER_TMP"/ 2>/dev/null || true
  echo ""
  exec "$0" "$BEFORE_TMP" "$AFTER_TMP"
fi

# Generic dir mode
BEFORE=${1:-build/test-results/testBefore}
AFTER=${2:-build/test-results/test}

echo "# QA Regression Report - $(date)" > "$SCRIPT_DIR/QA_Regression_Report.md"
echo "" >> "$SCRIPT_DIR/QA_Regression_Report.md"
echo "## BEFORE ($BEFORE)" >> "$SCRIPT_DIR/QA_Regression_Report.md"
echo "$(summary "$BEFORE")" >> "$SCRIPT_DIR/QA_Regression_Report.md"
echo "" >> "$SCRIPT_DIR/QA_Regression_Report.md"
echo "## AFTER ($AFTER)" >> "$SCRIPT_DIR/QA_Regression_Report.md"
echo "$(summary "$AFTER")" >> "$SCRIPT_DIR/QA_Regression_Report.md"
echo "" >> "$SCRIPT_DIR/QA_Regression_Report.md"

BEFORE_FAILED=$(summary "$BEFORE" | grep -o '[0-9]* failed' | grep -o '[0-9]*')
AFTER_FAILED=$(summary "$AFTER" | grep -o '[0-9]* failed' | grep -o '[0-9]*')

if [ "$AFTER_FAILED" != "0" ] && [ "$AFTER_FAILED" != "" ]; then
  if [ "$BEFORE_FAILED" = "0" ] || [ "$BEFORE_FAILED" = "" ]; then
    echo "### ❌ REGRESSION DETECTED - Before passed, After failed" >> "$SCRIPT_DIR/QA_Regression_Report.md"
  else
    echo "### ⚠️ STILL FAILING" >> "$SCRIPT_DIR/QA_Regression_Report.md"
  fi
else
  if [ "$BEFORE_FAILED" = "0" ] || [ "$BEFORE_FAILED" = "" ]; then
    echo "### ✅ NO REGRESSION" >> "$SCRIPT_DIR/QA_Regression_Report.md"
  else
    echo "### ✅ FIXED" >> "$SCRIPT_DIR/QA_Regression_Report.md"
  fi
fi

echo "" >> "$SCRIPT_DIR/QA_Regression_Report.md"
echo "Generated from: $BEFORE vs $AFTER" >> "$SCRIPT_DIR/QA_Regression_Report.md"
cat "$SCRIPT_DIR/QA_Regression_Report.md"
