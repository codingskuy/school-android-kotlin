package io.codingskuy.profilepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.codingskuy.profilepage.databinding.ItemProfileBinding

class ProfileAdapter(val items: List<ProfileItem>):
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileViewHolder {
        val binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProfileViewHolder,
        position: Int
    ) {
        holder.binding.item = items[position]
    }

    override fun getItemCount(): Int = items.size

    class ProfileViewHolder(val binding: ItemProfileBinding) : RecyclerView.ViewHolder(binding.root)
}