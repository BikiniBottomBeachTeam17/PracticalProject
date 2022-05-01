# echarts 图表 <span style="font-size:16px;">👑👑👑👑👑 <span style="background:#ff9d00;padding:2px 4px;color:#fff;font-size:10px;border-radius: 3px;">全端</span></span>
> 一个基于 JavaScript 的开源可视化图表库   [查看更多 站点1](https://limeui.qcoon.cn/#/echart) |  [查看更多 站点2](http://liangei.gitee.io/limeui/#/echart)  <br>
> 基于 echarts 做了兼容处理，更多示例请访问  [uni示例 站点1](https://limeui.qcoon.cn/#/echart-example) | [uni示例 站点2](http://liangei.gitee.io/limeui/#/echart-example) | [官方示例](https://echarts.apache.org/examples/zh/index.html)     <br>
> Q群：1046793420 <br>

## 平台兼容

| H5  | 微信小程序 | 支付宝小程序 | 百度小程序 | 头条小程序 | QQ 小程序 | App  |
| --- | ---------- | ------------ | ---------- | ---------- | --------- | ---- |
| √   | √          | √         | √      | √       | √      | √ |


## 安装
在uniapp 插件市场 找到 [百度图表](https://ext.dcloud.net.cn/plugin?id=4899) 导入


### npm 安装 echarts
```cmd
pnpm add echarts
 -or-
npm install echarts
```

**注意** 
* 🔔 必须使用hbuilderx 3.4.8-alpha以上
* 🔔 echarts 5.3.0

## 代码演示
### 基础用法
```html
<view style="width: 100%; height:500rpx"><l-echart ref="chart"></l-echart></view>
```

```js
// 引入全量包
import * as echarts from 'echarts'

// 或者按需引入 
import * as echarts from 'echarts/core';
import {LineChart} from 'echarts/charts';
import {TitleComponent,TooltipComponent,GridComponent, DatasetComponent, TransformComponent, LegendComponent } from 'echarts/components';
// 标签自动布局，全局过渡动画等特性
import {LabelLayout,UniversalTransition} from 'echarts/features';
// 引入 Canvas 渲染器，注意引入 CanvasRenderer 或者 SVGRenderer 是必须的一步
import {CanvasRenderer} from 'echarts/renderers';

// 注册必须的组件
echarts.use([
	LegendComponent,
	TitleComponent,
	TooltipComponent,
	GridComponent,
	DatasetComponent,
	TransformComponent,
	LineChart,
	LabelLayout,
	UniversalTransition,
	CanvasRenderer
]);
export default {
	data() {
		return {
			option: {
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'shadow' 
					},
					confine: true
				},
				legend: {
					data: ['热度', '正面', '负面']
				},
				grid: {
					left: 20,
					right: 20,
					bottom: 15,
					top: 40,
					containLabel: true
				},
				xAxis: [
					{
						type: 'value',
						axisLine: {
							lineStyle: {
								color: '#999999'
							}
						},
						axisLabel: {
							color: '#666666'
						}
					}
				],
				yAxis: [
					{
						type: 'category',
						axisTick: { show: false },
						data: ['汽车之家', '今日头条', '百度贴吧', '一点资讯', '微信', '微博', '知乎'],
						axisLine: {
							lineStyle: {
								color: '#999999'
							}
						},
						axisLabel: {
							color: '#666666'
						}
					}
				],
				series: [
					{
						name: '热度',
						type: 'bar',
						label: {
							normal: {
								show: true,
								position: 'inside'
							}
						},
						data: [300, 270, 340, 344, 300, 320, 310],
					},
					{
						name: '正面',
						type: 'bar',
						stack: '总量',
						label: {
							normal: {
								show: true
							}
						},
						data: [120, 102, 141, 174, 190, 250, 220]
					},
					{
						name: '负面',
						type: 'bar',
						stack: '总量',
						label: {
							normal: {
								show: true,
								position: 'left'
							}
						},
						data: [-20, -32, -21, -34, -90, -130, -110]
					}
				]
			},
		};
	},
	mounted() {
		this.$refs.chart.init(echarts, chart => {
			canvas.setChart(chart);
			chart.setOption(this.option);
		});
	}
}
```

## 数据更新
- 使用 `ref` 可获取`setOption`设置更新

```js
this.$refs.chart.setOption(data)
```

## nvue
- 由于`nvue`不支持 `canvas` 所以插件使用的是`webview`
- 1、把插件 `static`里的`index.html` 放到 项目根目录的 `hybrid/html/lime-chart/index.html`
- 2、把`index.html`里的script src远程文件下载下来放到`hybrid/html/lime-chart/`并修改成相对路径
- 3、把插件`l-echart.vue`文件里 web-view 的 src 修改成 `/hybrid/html/lime-chart/index.html`

## 常见问题
- 微信小程序 `2d` 不支持 真机调试
- 头条系小程序 `2d` 不支持 开发工具预览，也存在划动图表页面无法滚动的问题。
- `toolbox` 不支持
- `echarts-gl` 不支持

## Props

| 参数             | 说明                                                            | 类型             | 默认值        | 版本 	|
| ---------------  | --------                                                        | -------         | ------------ | ----- 	|
| custom-style     | 自定义样式                                                      |   `string`       | -            | -     	|
| type             | 指定 canvas 类, 仅支持微信和头条                                 |    `string`      | `2d`         |   	    |
| is-clickable     | 是否派发点击事件                                                 |    `boolean`     | `true`     |   	    |
| is-disable-scroll | 触摸图表时是否禁止页面滚动                                       |    `boolean`     | `false`     |   	    |
| is-clickable     | 是否派发点击事件                                                 |    `boolean`     | `true`     |   	    |
| is-renderjs     | app-vue 使用 renderjs 渲染                                       |    `boolean`     | `false`     |   	    |

## 事件

| 参数                    | 说明                                                                                                             |
| ---------------        | ---------------                                                                                                  |
| init(echarts, callback)  | 初始化调用函数，**callback**: 回调函数                                           |  
| setChart(chart)        | 已经初始化后，请使用这个函数，**chart**：回调函数参数是图表实例                    |  
| setOption(data)        | [图表配置项](https://echarts.apache.org/zh/option.html#title)，用于更新 ，传递是数据 `option`  |  
| clear()                | 清空当前实例，会移除实例中所有的组件和图表。  |  
| dispose()              | 销毁实例  |  
| showLoading()          | 显示加载  |  
| hideLoading()          | 隐藏加载  |  
| canvasToTempFilePath(opt)  | 用于生成图片  |  


## 打赏
如果你觉得本插件，解决了你的问题，赠人玫瑰，手留余香。  

![输入图片说明](https://cdn.jsdelivr.net/gh/liangei/image@latest/222521_bb543f96_518581.jpeg "微信图片编辑_20201122220352.jpg")