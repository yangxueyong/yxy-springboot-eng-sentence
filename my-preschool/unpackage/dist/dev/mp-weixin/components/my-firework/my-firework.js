"use strict";
const common_vendor = require("../../common/vendor.js");
const duration = 5e3;
const _sfc_main = {
  __name: "my-firework",
  setup(__props) {
    const canvasWidth = common_vendor.ref(300);
    const canvasHeight = common_vendor.ref(500);
    const particles = common_vendor.ref([]);
    const animationTimer = common_vendor.ref(null);
    const startTime = common_vendor.ref(0);
    const initParticles = () => {
      const count = 50;
      particles.value = [];
      for (let i = 0; i < count; i++) {
        particles.value.push({
          x: Math.random() * canvasWidth.value,
          y: -10,
          // 从屏幕上方开始
          vx: Math.random() * 2 - 1,
          // 水平速度
          vy: Math.random() * 2 + 2,
          // 垂直速度
          size: Math.random() * 3 + 2,
          color: getRandomColor(),
          alpha: 1,
          decay: Math.random() * 0.02 + 0.01
          // 透明度衰减
        });
      }
    };
    const getRandomColor = () => {
      const colors = [
        "#FF5252",
        "#FF4081",
        "#E040FB",
        "#7C4DFF",
        "#536DFE",
        "#448AFF",
        "#40C4FF",
        "#18FFFF",
        "#64FFDA",
        "#69F0AE",
        "#B2FF59",
        "#EEFF41",
        "#FFFF00",
        "#FFD740",
        "#FFAB40",
        "#FF6E40"
      ];
      return colors[Math.floor(Math.random() * colors.length)];
    };
    const drawParticles = () => {
      const ctx = common_vendor.index.createCanvasContext("fireworksCanvas", common_vendor.getCurrentInstance());
      ctx.clearRect(0, 0, canvasWidth.value, canvasHeight.value);
      particles.value.forEach((particle) => {
        ctx.beginPath();
        ctx.arc(particle.x, particle.y, particle.size, 0, Math.PI * 2);
        ctx.setFillStyle(particle.color);
        ctx.setGlobalAlpha(particle.alpha);
        ctx.fill();
      });
      ctx.draw();
    };
    const updateParticles = () => {
      const currentTime = Date.now();
      const elapsed = currentTime - startTime.value;
      if (elapsed >= duration) {
        stopAnimation();
        return false;
      }
      particles.value.forEach((particle) => {
        particle.x += particle.vx;
        particle.y += particle.vy;
        particle.alpha -= particle.decay;
        particle.vx += (Math.random() - 0.5) * 0.2;
        particle.vy += Math.random() * 0.1;
        if (particle.y > canvasHeight.value || particle.alpha <= 0) {
          resetParticle(particle);
        }
      });
      return true;
    };
    const resetParticle = (particle) => {
      particle.x = Math.random() * canvasWidth.value;
      particle.y = -10;
      particle.alpha = 1;
      particle.vx = Math.random() * 2 - 1;
      particle.vy = Math.random() * 2 + 2;
    };
    const animate = () => {
      const shouldContinue = updateParticles();
      drawParticles();
      common_vendor.index.__f__("log", "at components/my-firework/my-firework.vue:110", "进来了 大哥");
      if (shouldContinue) {
        animationTimer.value = setTimeout(() => {
          animate();
        }, 16);
      }
    };
    const stopAnimation = () => {
      if (animationTimer.value) {
        clearTimeout(animationTimer.value);
        animationTimer.value = null;
      }
      const ctx = common_vendor.index.createCanvasContext("fireworksCanvas", common_vendor.getCurrentInstance());
      ctx.clearRect(0, 0, canvasWidth.value, canvasHeight.value);
      ctx.draw();
    };
    const startAnimation = () => {
      startTime.value = Date.now();
      initParticles();
      animate();
    };
    common_vendor.onMounted(() => {
      common_vendor.index.getSystemInfo({
        success: (res) => {
          canvasWidth.value = res.windowWidth;
          canvasHeight.value = res.windowHeight * 0.8;
          startAnimation();
        }
      });
    });
    common_vendor.onUnmounted(() => {
      stopAnimation();
    });
    return (_ctx, _cache) => {
      return {
        a: canvasWidth.value + "px",
        b: canvasHeight.value + "px"
      };
    };
  }
};
wx.createComponent(_sfc_main);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/components/my-firework/my-firework.js.map
