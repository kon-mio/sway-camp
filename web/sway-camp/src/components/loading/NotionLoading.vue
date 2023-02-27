<template>
  <div class="loading">
    <div class="loading-spinner">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        xmlns:xlink="http://www.w3.org/1999/xlink"
        xmlns:sketch="http://www.bohemiancoding.com/sketch/ns"
        width="100%"
        height="100%"
        viewBox="0 0 24 24"
        version="1.1"
        data-ember-extension="1"
      >
        <defs>
          <linearGradient
            id="linearGradient-1"
            x1="28.1542969%"
            y1="63.7402344%"
            x2="74.6289062%"
            y2="17.7832031%"
          >
            <stop stop-color="rgba(164, 164, 164, 1)" offset="0%" />
            <stop stop-color="rgba(164, 164, 164, 0)" stop-opacity="0" offset="100%" />
          </linearGradient>
        </defs>
        <g id="Page-1" stroke="none" stroke-width="1" fill="none">
          <g transform="translate(-236.000000, -286.000000)">
            <g transform="translate(238.000000, 286.000000)">
              <circle
                id="Oval-2"
                stroke="url(#linearGradient-1)"
                stroke-width="4"
                cx="10"
                cy="12"
                r="10"
              />
              <path
                id="Oval-2"
                d="M10,2 C4.4771525,2 0,6.4771525 0,12"
                stroke="rgba(164, 164, 164, 1)"
                stroke-width="4"
              />
              <rect
                id="Rectangle-1"
                fill="rgba(164, 164, 164, 1)"
                x="8"
                y="0"
                width="4"
                height="4"
                rx="8"
              />
            </g>
          </g>
        </g>
      </svg>
    </div>
  </div>
</template>

<style lang="less" scoped>
.loading {
  position: absolute;
  inset: 0px;
  display: flex;
  align-items: center;
  justify-content: center;
  pointer-events: none;
  height: 100%;
  &-spinner {
    -webkit-animation: rotate 1000ms-linear infinite;
    animation: rotate 10s linear infinite;
    -webkit-transform-origin: center center;
    transform-origin: center center;
    width: 1.375em;
    height: 1.375em;
    opacity: 0.5;
    display: block;
    pointer-events: none;
  }
}

@keyframes rotate {
  0% {
    transform: rotate(0deg) translateZ(0);
  }
  100% {
    transform: rotate(3600deg) translateZ(0);
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
