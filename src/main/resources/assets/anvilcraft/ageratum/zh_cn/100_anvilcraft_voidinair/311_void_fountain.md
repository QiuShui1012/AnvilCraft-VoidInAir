---
navigation:
  title: "§5虚空涌泉"
  icon: "voidinair:void_fountain"
items:
  - voidinair:void_fountain
---

# <ref item="voidinair:void_fountain"/>

## 制造

<row halign="center">
<item id="anvilcraft:mineral_fountain"/>
<item id="anvilcraft:impact_pile"/>
<item id="anvilcraft:void_matter_block"/>
<item id="anvilcraft:transcendence_anvil"/>
</row>

1. 在世界最低建筑高度上方第 1 至第 6 格放置<ref item="anvilcraft:mineral_fountain"/>
2. 在其正上方依次放置<ref item="anvilcraft:impact_pile"/>和<ref item="anvilcraft:void_matter_block"/>
3. 让<ref item="anvilcraft:transcendence_anvil"/>从至少 20 格的高度落下并砸中虚空物质块
4. 超限铁砧、虚空物质块和冲击桩会被消耗，矿物涌泉则转化为虚空涌泉

## 生产

- 默认每 600 游戏刻（30 秒）尝试在正上方生成一个方块
- 仅在世界底部附近工作，并要求正上方为空气
- 四个水平相邻面中至少留出一面空气，即可进行常规生产
- 四面都是实体方块但方块种类不同时，涌泉会停止生产
- 四面的方块状态完全相同时，涌泉有 3% 概率复制该方块

|  随机区间  | 产物                                                   |
|:----------:|:-------------------------------------------------------|
|   前 20%   | <ref item="anvilcraft:void_stone"/>                    |
| 接下来 20% | <ref item="anvilcraft:earth_core_shard_ore"/>          |
| 接下来 3%  | 四面结构有效时，复制周围方块                           |
|  剩余区间  | 随机的[虚空衰变](../001_feature/101_void_decay.md)产物 |

<info>
四面复制结构无效时，对应的 3% 区间也会改为生成虚空衰变产物。
</info>
