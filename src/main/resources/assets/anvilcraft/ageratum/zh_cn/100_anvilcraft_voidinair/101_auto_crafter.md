---
navigation:
  title: "§2自动合成器"
  icon: "voidinair:auto_crafter"
items:
  - voidinair:auto_crafter
---

# <ref item="voidinair:auto_crafter"/>

## 获取

手持<ref item="minecraft:crafting_table"/>潜行右击任意[批量合成装置](../004_block/101_batch_craft.md)。工作台会被消耗，原有工作方块会返还，装置则转化为自动合成器。

也可以通过以下配方将其还原为批量合成器：

<recipe id="voidinair:batch_crafter_convert"/>

## 功能

- 持续耗电 4 kW，默认每 20 游戏刻尝试合成一次
- 没有红石信号时工作；收到红石信号会暂停合成
- 九个物品槽会按照 3x3 合成网格进行匹配
- 同一原料排列匹配到多个配方时，可以用结果旁的循环按钮选择配方
- 每次操作会按照所有非空输入槽中的最少物品数批量合成，并一次性消耗原料
- 优先向设定输出面相邻的容器输出；没有容器时会将产物喷出
- 输出容器空间不足或喷出口被阻挡时，会等待而不消耗原料

## 过滤

- 启用过滤后，可以在自动输入原料时保持合成配方的排列
- 为需要的槽位设置虚影物品，并禁用不使用的槽位
- 在已过滤槽位上滚动滚轮可设置堆叠上限；按住 Shift 可以加速调整
