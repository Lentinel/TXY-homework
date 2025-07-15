import { defineStore } from 'pinia'

export const useFavoritesStore = defineStore('favorites', {
  state: () => ({ ids: [] }),
  actions: {
    add(id)    { if (!this.ids.includes(id)) this.ids.push(id) },
    remove(id) { this.ids = this.ids.filter(x => x !== id) },
    isFavorite(id) { return this.ids.includes(id) }
  }
})
