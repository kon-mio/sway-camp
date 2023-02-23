// 存储
export const storage = {
  set(key: string, value: string): void {
    localStorage.setItem(key, JSON.stringify(value))
  },
  get(key: string): unknown {
    const jsonValue = localStorage.getItem(key) || null
    return jsonValue ? JSON.parse(jsonValue) : null
  },
  remove(key: string): void {
    localStorage.removeItem(key)
  }
}
