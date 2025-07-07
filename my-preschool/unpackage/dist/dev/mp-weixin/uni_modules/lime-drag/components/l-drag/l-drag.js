"use strict";
const common_vendor = require("../../../../common/vendor.js");
const uni_modules_limeDrag_components_lDrag_props = require("./props.js");
const _sfc_main = common_vendor.defineComponent({
  name: "l-drag",
  externalClasses: ["l-class"],
  options: {
    addGlobalClass: true,
    virtualHost: true
  },
  props: uni_modules_limeDrag_components_lDrag_props.DragProps,
  emits: ["change"],
  setup(props, { emit, expose }) {
    const app = common_vendor.getCurrentInstance();
    const isDrag = common_vendor.ref(false);
    const isInit = common_vendor.ref(false);
    const isReset = common_vendor.ref(true);
    const colmunId = common_vendor.ref(-1);
    const active = common_vendor.ref(-1);
    const maxIndex = common_vendor.ref(-1);
    const animation = common_vendor.ref(true);
    const isDisabled = common_vendor.ref(props.handle || props.longpress ? true : false);
    const dragEl = common_vendor.reactive({
      content: null,
      /** 当前视图下标*/
      index: 0,
      /** 旧视图下标 */
      oldindex: -1,
      /** 上次原始下标 */
      lastindex: -1
    });
    const ghostEl = common_vendor.reactive({
      content: null,
      x: 0,
      y: 0
    });
    const beforeEl = common_vendor.reactive({
      x: 0,
      y: 0
    });
    const afterEl = common_vendor.reactive({
      x: 0,
      y: 0
    });
    let gridRects = [];
    const areaWidth = common_vendor.ref(0);
    const cloneList = common_vendor.ref([]);
    const leaveRow = common_vendor.ref(0);
    const extra = common_vendor.computed(() => (props.before ? 1 : 0) + (props.after ? 1 : 0));
    const rows = common_vendor.computed(() => Math.ceil(((isInit.value ? cloneList.value.length : props.list.length) + extra.value) / props.column));
    const gridHeight = common_vendor.computed(() => props.aspectRatio ? girdWidth.value / props.aspectRatio : /rpx$/.test(`${props.gridHeight}`) ? common_vendor.index.upx2px(parseInt(`${props.gridHeight}`)) : parseInt(`${props.gridHeight}`));
    const girdWidth = common_vendor.computed(() => areaWidth.value / props.column);
    const viewStyles = common_vendor.computed(() => ({ width: girdWidth.value + "px", height: gridHeight.value + "px" }));
    const areaStyles = common_vendor.computed(() => ({ height: (rows.value + leaveRow.value) * gridHeight.value + "px" }));
    const innerStyles = common_vendor.computed(() => ({
      height: (rows.value + props.extraRow + leaveRow.value) * gridHeight.value + "px"
    }));
    const sleep = (cb, time = 1e3 / 60) => setTimeout(cb, time);
    const createGrid = (content, position) => {
      colmunId.value++;
      maxIndex.value++;
      const index = maxIndex.value;
      const colmun = gridRects[index];
      let x = 0;
      let y = 0;
      if (colmun) {
        if (props.after) {
          let nxet = gridRects[index + 1];
          if (!nxet) {
            nxet = createGridRect(gridRects.length + (props.before ? 1 : 0));
            gridRects.push(nxet);
          }
          setReset(() => setAfter(nxet));
        } else {
          setReset();
        }
        x = colmun.x;
        y = colmun.y;
      } else {
        const nxet = createGridRect(gridRects.length + (props.before ? 1 : 0));
        gridRects.push(nxet);
        setReset();
        x = nxet.x;
        y = nxet.y;
      }
      if (position) {
        x = position.x;
        y = position.y;
      }
      return { id: `l-drag-item-${colmunId.value}`, index, oldindex: index, content, x, y, class: "", show: true };
    };
    const setReset = (cb) => {
      if (isInit.value) {
        cb && sleep(cb);
      }
    };
    const setAfter = ({ x, y } = { x: 0, y: 0 }) => {
      if (props.after) {
        afterEl.x = x;
        afterEl.y = y;
      }
    };
    const setDisabled = (e, flag = false) => {
      const type = `${e.type}`.toLowerCase();
      const { handle = props.touchHandle } = e.target.dataset;
      if (props.handle && !handle) {
        isDisabled.value = true;
      } else if (props.handle && handle && !props.longpress) {
        isDisabled.value = flag;
      } else if (props.handle && handle && props.longpress && type.includes("longpress")) {
        isDisabled.value = false;
      } else if (props.longpress && type.includes("longpress") && !props.handle) {
        isDisabled.value = false;
      }
      if (type.includes("touchend") && props.longpress) {
        isDisabled.value = true;
      }
    };
    const createGridRect = (i, last) => {
      let { row } = last || gridRects[gridRects.length - 1] || { row: 0 };
      const col = i % props.column;
      const grid = (row2, x, y) => {
        return { row: row2, x, y, x1: x + girdWidth.value, y1: y + gridHeight.value };
      };
      if (col == 0 && i != 0) {
        row++;
      }
      return grid(row, col * girdWidth.value, row * gridHeight.value);
    };
    const createGridRects = () => {
      let rects = [];
      const length = rows.value * props.column + extra.value;
      gridRects = [];
      for (var i = 0; i < length; i++) {
        const item = createGridRect(i, rects[rects.length - 1]);
        rects.push(item);
      }
      if (props.before) {
        const { x, y } = rects.shift();
        beforeEl.x = x;
        beforeEl.y = y;
      }
      setAfter(rects[props.list.length]);
      gridRects = rects;
    };
    const updateList = (v) => {
      cloneList.value = v.map((content) => createGrid(content));
    };
    const touchStart = (e) => {
      var _a, _b;
      if (e.target.dataset.remove)
        return;
      const { oindex } = ((_a = e.currentTarget) == null ? void 0 : _a.dataset) || ((_b = e.target) == null ? void 0 : _b.dataset) || {};
      if (typeof oindex !== "number")
        return;
      const target = cloneList.value[oindex];
      isDrag.value = true;
      active.value = oindex;
      dragEl.index = dragEl.oldindex = target.index;
      ghostEl.x = target.x || 0;
      ghostEl.y = target.y || 0;
      dragEl.content = ghostEl.content = target.content;
    };
    const touchEnd = (e) => {
      setTimeout(() => {
        if (e.target.dataset.remove || active.value == -1)
          return;
        setDisabled(e, true);
        isDrag.value = false;
        const isEmit = dragEl.index !== dragEl.oldindex && dragEl.oldindex > -1;
        dragEl.lastindex = active.value;
        dragEl.oldindex = active.value = -1;
        const last = cloneList.value[dragEl.lastindex];
        const position = gridRects[dragEl.index];
        common_vendor.nextTick$1(() => {
          last.x = position.x + 1e-3;
          last.y = position.y + 1e-3;
          sleep(() => {
            last.x = position.x;
            last.y = position.y;
            isEmit && emitting();
          });
        });
      }, 80);
    };
    const emitting = () => {
      const clone = [...cloneList.value].sort((a, b) => a.index - b.index);
      emit("change", clone);
    };
    const touchMove = (e) => {
      if (!isDrag.value)
        return;
      let { oindex } = e.currentTarget.dataset;
      if (oindex != active.value)
        return;
      const { x, y } = e.detail;
      const centerX = x + girdWidth.value / 2;
      const centerY = y + gridHeight.value / 2;
      for (let i = 0; i < cloneList.value.length; i++) {
        const item = gridRects[i];
        if (centerX > item.x && centerX < item.x1 && centerY > item.y && centerY < item.y1) {
          ghostEl.x = item.x;
          ghostEl.y = item.y;
          if (dragEl.index != i) {
            _move(active.value, i);
          }
          break;
        }
      }
    };
    const getDragEl = (oindex) => {
      if (isDrag.value) {
        return dragEl;
      }
      return cloneList.value[oindex];
    };
    const _move = (oindex, toIndex, position, emit2 = true) => {
      const length = cloneList.value.length - 1;
      if (toIndex > length || toIndex < 0)
        return;
      const dragEl2 = getDragEl(oindex);
      let speed = 0;
      let start = dragEl2.index;
      if (start < toIndex) {
        speed = 1;
      }
      if (start > toIndex) {
        speed = -1;
      }
      if (!speed)
        return;
      let distance = start - toIndex;
      while (distance) {
        distance += speed;
        const target = isDrag.value ? dragEl2.index += speed : start += speed;
        let targetOindex = cloneList.value.findIndex((item) => item.index == target && item.content != dragEl2.content);
        if (targetOindex == oindex)
          return;
        if (targetOindex < 0) {
          targetOindex = cloneList.value.length - 1;
        }
        let targetEl = cloneList.value[targetOindex];
        if (!targetEl)
          return;
        const lastIndex = target - speed;
        const activeEl = cloneList.value[oindex];
        const rect = gridRects[lastIndex];
        targetEl.x = rect.x;
        targetEl.y = rect.y;
        targetEl.oldindex = targetEl.index;
        targetEl.index = lastIndex;
        activeEl.oldindex = activeEl.index;
        activeEl.index = toIndex;
        if (!distance && !isDrag.value) {
          const rect2 = gridRects[toIndex];
          const { x, y } = position || rect2;
          activeEl.x = dragEl2.x = x;
          activeEl.y = dragEl2.y = y;
          if (emit2) {
            emitting();
          }
        }
      }
    };
    const move = (oindex, toIndex) => {
      active.value = -1;
      isDrag.value = false;
      _move(oindex, toIndex);
    };
    const REMOVE_TIME = 400;
    let removeTimer = null;
    const remove = (oindex) => {
      active.value = -1;
      isDrag.value = false;
      clearTimeout(removeTimer);
      const item = cloneList.value[oindex];
      if (props.disabled || !item)
        return;
      item.show = false;
      const after = cloneList.value.length - 1;
      _move(oindex, after, item, false);
      setAfter(gridRects[after]);
      maxIndex.value--;
      const _remove = (_index = oindex) => {
        const row = Math.ceil((cloneList.value.length - 1 + extra.value) / props.column);
        if (row < rows.value) {
          leaveRow.value = rows.value - row;
        }
        cloneList.value.splice(_index, 1)[0];
        emitting();
        removeTimer = setTimeout(() => {
          leaveRow.value = 0;
        }, REMOVE_TIME);
      };
      _remove();
    };
    const push = (...args) => {
      if (props.disabled)
        return;
      if (Array.isArray(args)) {
        Promise.all(args.map(async (item) => await add(item, true))).then(emitting);
      }
    };
    const add = (content, after) => {
      return new Promise((resolve) => {
        const item = createGrid(content, after ? null : { x: -100, y: 0 });
        item.class = "l-drag-enter";
        cloneList.value.push(item);
        const length = cloneList.value.length - 1;
        common_vendor.nextTick$1(() => {
          sleep(() => {
            item.class = "l-drag-leave";
            _move(length, after ? length : 0, null, false);
            common_vendor.triggerRef(cloneList);
            resolve(true);
          });
        });
      });
    };
    const unshift = (...args) => {
      if (props.disabled)
        return;
      if (Array.isArray(args)) {
        Promise.all(args.map(async (item) => await add(item))).then(emitting);
      }
    };
    const shift = () => {
      if (!cloneList.value.length)
        return;
      remove(cloneList.value.findIndex((item) => item.index == 0) || 0);
    };
    const pop = () => {
      const length = cloneList.value.length - 1;
      if (length < 0)
        return;
      remove(cloneList.value.findIndex((item) => item.index == length) || length);
    };
    const clear = () => {
      isInit.value = isDrag.value = false;
      maxIndex.value = colmunId.value = active.value = -1;
      cloneList.value = [];
      gridRects = [];
    };
    const init = () => {
      clear();
      createGridRects();
      common_vendor.nextTick$1(() => {
        updateList(props.list);
        isInit.value = true;
      });
    };
    const getRect = () => {
      common_vendor.index.createSelectorQuery().in(app.proxy).select(".l-drag").boundingClientRect((res) => {
        if (res) {
          areaWidth.value = res.width || 0;
          init();
        }
      }).exec();
    };
    common_vendor.onMounted(getRect);
    common_vendor.onUnmounted(clear);
    common_vendor.watch(() => props.list, init);
    expose({
      remove,
      // add,
      move,
      push,
      unshift,
      shift,
      pop
    });
    return {
      cloneList,
      areaStyles,
      innerStyles,
      viewStyles,
      setDisabled,
      isDisabled,
      isReset,
      isDrag,
      active,
      animation,
      afterEl,
      ghostEl,
      beforeEl,
      touchStart,
      touchMove,
      touchEnd,
      remove,
      // add,
      move,
      push,
      unshift,
      // shift,
      // pop,
      props
      // isDelete: props.delete,
      // ...toRefs(props)
    };
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: _ctx.isReset
  }, _ctx.isReset ? common_vendor.e({
    b: _ctx.isDrag && _ctx.props.ghost
  }, _ctx.isDrag && _ctx.props.ghost ? {
    c: common_vendor.s(_ctx.viewStyles),
    d: _ctx.ghostEl.x,
    e: _ctx.ghostEl.y
  } : {}, {
    f: _ctx.props.before
  }, _ctx.props.before ? {
    g: common_vendor.s(_ctx.viewStyles),
    h: _ctx.beforeEl.x,
    i: _ctx.beforeEl.y
  } : {}, {
    j: common_vendor.f(_ctx.cloneList, (item, oindex, i0) => {
      return common_vendor.e({
        a: "grid-" + i0,
        b: common_vendor.r("grid", {
          oindex,
          index: item.index,
          oldindex: item.oldindex,
          content: item.content,
          active: !_ctx.isDisabled && !_ctx.isDisabled && oindex == _ctx.active
        }, i0)
      }, !(_ctx.isDisabled || _ctx.props.disabled) && _ctx.props.longpress ? {} : {}, {
        c: item.id,
        d: oindex,
        e: common_vendor.n({
          "l-is-active": oindex == _ctx.active,
          "l-is-hidden": !item.show
        }),
        f: common_vendor.n(item.class),
        g: item.x,
        h: item.y,
        i: common_vendor.o((...args) => _ctx.touchStart && _ctx.touchStart(...args), item.id),
        j: common_vendor.o((...args) => _ctx.touchMove && _ctx.touchMove(...args), item.id),
        k: common_vendor.o((...args) => _ctx.touchEnd && _ctx.touchEnd(...args), item.id),
        l: common_vendor.o((...args) => _ctx.touchEnd && _ctx.touchEnd(...args), item.id),
        m: common_vendor.o((...args) => _ctx.setDisabled && _ctx.setDisabled(...args), item.id)
      });
    }),
    k: !(_ctx.isDisabled || _ctx.props.disabled) && _ctx.props.longpress,
    l: common_vendor.s(_ctx.viewStyles),
    m: _ctx.friction,
    n: _ctx.damping,
    o: _ctx.animation,
    p: _ctx.isDisabled || _ctx.props.disabled,
    q: _ctx.props.after
  }, _ctx.props.after ? {
    r: common_vendor.s(_ctx.viewStyles),
    s: _ctx.afterEl.x,
    t: _ctx.afterEl.y
  } : {}, {
    v: common_vendor.s(_ctx.innerStyles)
  }) : {}, {
    w: common_vendor.s(_ctx.areaStyles),
    x: common_vendor.o((...args) => _ctx.setDisabled && _ctx.setDisabled(...args))
  });
}
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createComponent(Component);
//# sourceMappingURL=../../../../../.sourcemap/mp-weixin/uni_modules/lime-drag/components/l-drag/l-drag.js.map
