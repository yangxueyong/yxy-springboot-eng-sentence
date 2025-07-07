"use strict";
const common_vendor = require("../vendor.js");
const canvasWidth = common_vendor.ref(300);
const canvasHeight = common_vendor.ref(500);
const showCanvas = common_vendor.ref(false);
const particles = common_vendor.ref([]);
const animationTimer = common_vendor.ref(null);
const startTime = common_vendor.ref(0);
const duration = 5e3;
let ctx = null;
const initParticles = () => {
  const count = 100;
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
  if (ctx == null) {
    ctx = common_vendor.index.createCanvasContext("fireworksCanvas", void 0);
  }
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
  showCanvas.value = false;
  if (ctx != null) {
    common_vendor.index.__f__("log", "at common/utils/firework.js:117", "清理画布-->");
    ctx.clearRect(0, 0, canvasWidth.value, canvasHeight.value);
    ctx.draw();
  }
};
const startAnimation = () => {
  startTime.value = Date.now();
  initParticles();
  animate();
};
const startFirework = () => {
  showCanvas.value = true;
  common_vendor.index.getSystemInfo({
    success: (res) => {
      canvasWidth.value = res.windowWidth;
      canvasHeight.value = res.windowHeight * 0.8;
      startAnimation();
    }
  });
};
const endFirework = () => {
  stopAnimation();
};
exports.canvasHeight = canvasHeight;
exports.canvasWidth = canvasWidth;
exports.endFirework = endFirework;
exports.showCanvas = showCanvas;
exports.startFirework = startFirework;
//# sourceMappingURL=../../../.sourcemap/mp-weixin/common/utils/firework.js.map
