import { isRef, ref, unref, watch, getCurrentScope, onScopeDispose } from "vue"
import { isClient, isFunction } from "@/utils/data/valid"
import type { Fn, MaybeComputedRef, Pausable } from "../utils/data/types"

/**
 * Get the value of value/ref/getter.
 */
function resolveUnref<T>(r: MaybeComputedRef<T>): T {
  return typeof r === "function" ? (r as any)() : unref(r)
}

/**
 * Call onScopeDispose() if it's inside an effect scope lifecycle, if not, do nothing
 *
 * @param fn
 */
export function tryOnScopeDispose(fn: Fn) {
  if (getCurrentScope()) {
    onScopeDispose(fn)
    return true
  }
  return false
}

export interface UseIntervalFnOptions {
  /**
   * Start the timer immediately
   *
   * @default true
   */
  immediate?: boolean

  /**
   * Execute the callback immediate after calling this function
   *
   * @default false
   */
  immediateCallback?: boolean
}

/**
 * Wrapper for `setInterval` with controls
 *
 * @param cb
 * @param interval
 * @param options
 */
export function useInterval(
  cb: Fn,
  interval: MaybeComputedRef<number> = 1000,
  options: UseIntervalFnOptions = {}
): Pausable {
  const { immediate = true, immediateCallback = false } = options

  let timer: ReturnType<typeof setInterval> | null = null
  const isActive = ref(false)

  function clean() {
    if (timer) {
      clearInterval(timer)
      timer = null
    }
  }

  function pause() {
    isActive.value = false
    clean()
  }

  function resume() {
    const intervalValue = resolveUnref(interval)
    if (intervalValue <= 0) return
    isActive.value = true
    if (immediateCallback) cb()
    clean()
    timer = setInterval(cb, intervalValue)
  }

  if (immediate && isClient) resume()

  if (isRef(interval) || isFunction(interval)) {
    const stopWatch = watch(interval, () => {
      if (isActive.value && isClient) resume()
    })
    tryOnScopeDispose(stopWatch)
  }

  tryOnScopeDispose(pause)

  return {
    isActive,
    pause,
    resume
  }
}
