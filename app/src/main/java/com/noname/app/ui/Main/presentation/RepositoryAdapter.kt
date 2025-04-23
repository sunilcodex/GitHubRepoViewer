package com.noname.app.ui.Main.presentation

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.noname.app.data.Model.RepositoryDto
import com.noname.app.databinding.ItemLoadingBinding
import com.noname.app.databinding.ItemRepositoryBinding

class RepositoryAdapter(
    private val repositories: MutableList<RepositoryDto>,
    private val onRepositoryClick: (RepositoryDto) -> Unit,
    private val onLoadMore: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_REPOSITORY = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    inner class RepositoryViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: RepositoryDto) {


            val url: String = repository.owner.avatarUrl
            Glide.with(binding.profileImg).load(url).into(binding.profileImg)

            binding.tvRepoName.text = repository.name

            repository.description?.let { description ->
                if (description.isNotBlank()) {
                    binding.tvRepoDescription.visibility = View.VISIBLE
                    binding.tvRepoDescription.text = repository.description
                } else {
                    binding.tvRepoDescription.visibility = View.GONE
                }
            } ?: run {
                binding.tvRepoDescription.visibility = View.GONE
            }

            repository.language?.let { language ->
                if (language.isNotBlank()) {
                    binding.tvLanguage.visibility = View.VISIBLE
                    binding.tvLanguage.setheading(repository.language)
                    binding.tvLanguage.setComplite(true)
                } else {
                    binding.tvLanguage.visibility = View.GONE
                }
            } ?: run {
                binding.tvLanguage.visibility = View.GONE
            }

            repository.stars?.let { stars ->
                if (stars > 0) {
                    binding.tvStars.visibility = View.VISIBLE
                    binding.tvStars.setheading(stars.toString())
                    binding.tvStars.setComplite(true)
                } else {
                    binding.tvStars.visibility = View.GONE
                }
            }


            repository.forks?.let { forks ->
                if (forks > 0) {
                    binding.tvForks.visibility = View.VISIBLE
                    binding.tvForks.setheading(forks.toString())
                    binding.tvForks.setComplite(true)
                } else {
                    binding.tvForks.visibility = View.GONE
                }
            }



            binding.onlineView.setSelected(repository.archived)
            binding.onlineView.setVisibility(if (repository.archived) View.VISIBLE else View.GONE)


            itemView.setOnClickListener {
                onRepositoryClick(repository)
            }
        }
    }

    inner class LoadingViewHolder(binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_REPOSITORY -> RepositoryViewHolder(
                ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            VIEW_TYPE_LOADING -> LoadingViewHolder(
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RepositoryViewHolder) {
            holder.bind(repositories[position])
        }

        // Load more trigger
        if (position == repositories.size - 1 && repositories[position].id != -1L) {
            onLoadMore()
        }
    }

    override fun getItemCount(): Int = repositories.size

    override fun getItemViewType(position: Int): Int {
        return if (repositories[position].id == -1L) VIEW_TYPE_LOADING else VIEW_TYPE_REPOSITORY
    }

    fun setLoading(isLoading: Boolean) {
        val isAlreadyLoading = repositories.any { it.id == RepositoryDto.LOADING.id }

        if (isLoading && !isAlreadyLoading) {
            // Avoid crash by posting on main thread after layout
            Handler(Looper.getMainLooper()).post {
                repositories.add(RepositoryDto.LOADING)
                notifyItemInserted(repositories.size - 1)
            }
        } else if (!isLoading) {
            val index = repositories.indexOfFirst { it.id == RepositoryDto.LOADING.id }
            if (index != -1) {
                // Remove safely on main thread too
                Handler(Looper.getMainLooper()).post {
                    repositories.removeAt(index)
                    notifyItemRemoved(index)
                }
            }
        }
    }

    fun addRepositories(newRepositories: List<RepositoryDto>) {

        val loadingIndex = repositories.indexOfFirst { it.id == RepositoryDto.LOADING.id }
        val insertIndex = if (loadingIndex != -1) loadingIndex else repositories.size

        repositories.addAll(insertIndex, newRepositories)
        notifyItemRangeInserted(insertIndex, newRepositories.size)
    }
}
