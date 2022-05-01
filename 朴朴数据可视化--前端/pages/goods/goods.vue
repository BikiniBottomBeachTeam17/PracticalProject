<template>
	<view style="background-color: rgb(250, 250, 250);" :style="{height:screenHeight+'px'}">
		<!-- #ifndef MP-WEIXIN  -->
		<view class="nav">
			<i class="iconfont icon-fanhui" @tap="goBack()"></i>
			查询商品历史价格
		</view>
		<!-- #endif -->
		<!-- 商品信息 -->
		<view class="goodsInfo">
			<view class="goodsImg" :style="{backgroundImage:'url('+goodsInfo.img+')'}">
			</view>
			<view class="goodsTitle">
				<view class="goodsNameBox">
					{{goodsInfo.name}}
				</view>
				<view class="goodsSubTitle">
					{{goodsInfo.subtitle}}
				</view>
				<view class="goodsPrice">
					<text style="color: #000000;font-size: 28rpx;">最近价格：</text>￥{{goodsInfo.price}}
				</view>
			</view>
		</view>
		<!-- 价格波动显示 -->
		<view class="dataShow">
			<view class="dataTitle">
				<text>价格走势</text>
			</view>
			<view style="height:300rpx;width: 100%;" loading='true'>
				<l-f2 ref="chart"></l-f2>
			</view>
			<view class="minPriceBox">
				<text style="color: rgb(180,180,180)">历史最低价：</text>
				<text style="color: rgb(255, 0, 0);font-weight: bold;">￥{{minPrice}}</text>
			</view>
		</view>
		<!-- 获取今日数据 -->
		<view class="getDataBox" style="padding: 40rpx;">
			<u-button type="error" text="删除该物品数据" @click="delData()"></u-button>
			<view style="height: 20rpx;"></view>
			<u-button type="primary" text="采集今日数据" @click="showToast()"></u-button>
			<view class="tips" style="text-align: center;font-size: 20rpx;color: #c8c8c8;margin-top: 40rpx;">
				<text>如果数据库中未存在当前物品今天的数据\n可点击此按钮获取今日数据并存储到数据库中\n\n</text>
				<text style="color: red;margin-top:20rpx;font-weight: bolder;">注:若系统中已经存在今日数据，不执行采集</text>
			</view>
		</view>


		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	import F2 from '@/uni_modules/lime-f2/components/l-f2/f2.min.js';
	import request from '../../utils/request.js'
	export default {
		data() {
			return {
				show: true,
				screenHeight: '',
				goodsID: '',
				resUrl: '',
				goodsInfo: {},
				minPrice: 49.9,
				priceData: []
			}
		},
		onLoad(option) {
			this.goodsID = option.goodsID
			this.resUrl =
				'https://j1.pupuapi.com/client/product/storeproduct/detail/7c1208da-907a-4391-9901-35a60096a3f9/' + this
				.goodsID
			var self = this;
			uni.request({
				url: '/api/getData/getPrice?uuid=' + this.goodsID,
				method: 'GET',
				success: res => {
					self.priceData = res.data.data
					self.$refs.chart.changeData(self.priceData)
					self.minPrice = Math.min.apply(Math, self.priceData.map(function(o) {
						return o.price
					}))
				}
			})
			uni.request({
				url: '/api/getData/getInfo?uuid=' + this.goodsID,
				success(res) {
					self.goodsInfo = res.data.data
					console.log(res.data.data)
				}
			})

		},
		onReady() {
			//获得所有数据
			this.screenHeight = uni.getSystemInfoSync().windowHeight
			this.showDataF2()
			this.show = false
		},
		methods: {
			showToast() {
				let nowDate = new Date().toLocaleDateString()
				if (new Date(this.priceData[this.priceData.length - 1].querytime).getTime() ==
					new Date(new Date().toLocaleDateString()).getTime()) {
					this.$refs.uToast.show({
						type: 'warning',
						message: "数据库已有今日信息\n今日价格为￥" + this.priceData[this.priceData.length - 1].price,
					})
				} else {
					uni.request({
						url: this.resUrl,
						success: res => {
							console.log(res)
							if (res.data.errcode == 0) {
								this.priceData.push({
									querytime: nowDate,
									price: res.data.data.price / 100
								})
								this.goodsInfo.price = res.data.data.price / 100
								if (this.minPrice > res.data.data.price / 100) {
									this.minPrice = res.data.data.price/100
								}
								this.$refs.uToast.show({
									type: 'success',
									message: "数据采集成功\n今日价格为￥" + res.data.data.price / 100,
								})
								uni.request({
									url: '/api/getData/save',
									data: {
										uuid: this.goodsID,
										price: res.data.data.price / 100,
										querytime: nowDate
									},
									method: 'POST',
									success: saveRes => {
										console.log(saveRes)
									}
								})
								this.$refs.chart.changeData(this.priceData)
							} else {
								this.$refs.uToast.show({
									type: 'error',
									message: "数据采集失败",
								})
							}
						}
					})
				}

			},
			showDataF2() {
				this.$refs.chart.init(config => {
					const chart = new F2.Chart(Object.assign(config, {
						aria: true
					}));
					chart.source(this.priceData, {
						price: {
							tickCount: 5,
							min: 0
						},
						querytime: {
							type: 'timeCat',
							range: [0, 1],
							tickCount: 3
						}
					});
					chart.tooltip({
						custom: true,
						showXTip: true,
						showYTip: true,
						snap: true,
						crosshairsType: 'xy',
						crosshairsStyle: {
							lineDash: [2]
						}
					});
					chart.axis('querytime', {
						label: function label(text, index, total) {
							const textCfg = {};
							if (index === 0) {
								textCfg.textAlign = 'left';
							} else if (index === total - 1) {
								textCfg.textAlign = 'right';
							}
							return textCfg;
						}
					});
					chart.line().position('querytime*price').color('#ff5500');
					chart.render();
					return chart;
				});
			},
			goBack() {
				uni.redirectTo({
					url: '../index/index'
				})
			},
			delData() {
				var self = this
				uni.request({
					url: '/api/getData/delete',
					data: self.goodsID,
					method: 'POST',
					success(res) {
						setTimeout((self.$refs.uToast.show({
							type: 'success',
							message: "该物品删除成功",
						})),1000)
						uni.redirectTo({
							url: '../index/index'
						})
						console.log(res)
					}
				})
			}
		},


	}
</script>

<style lang="scss">
	.nav {
		height: 80rpx;
		line-height: 80rpx;
		box-sizing: border-box;
		padding-left: 20rpx;
		background-color: rgb(12, 55, 97);
		color: #FFFFFF;
		font-weight: bolder;
	}

	.goodsInfo {
		height: 250rpx;
		background-color: #FFFFFF;
		position: relative;

		.goodsImg {
			height: 250rpx;
			width: 250rpx;
			background-repeat: no-repeat;
			background-size: 100% 100%;
			float: left;
		}

		.goodsTitle {
			position: absolute;
			top: calc(50% - 95rpx);
			right: 0;
			float: right;
			width: calc(100% - 250rpx);
			box-sizing: border-box;

			.goodsNameBox {
				white-space: normal;
				overflow: hidden;
				text-overflow: ellipsis;
				-webkit-line-clamp: 2;
				/* 设置超出多少行隐藏 */
				-webkit-box-orient: vertical;
				display: -webkit-box;
				font-weight: bolder;
			}

			.goodsSubTitle {
				font-size: 28rpx;
				color: #b4b4b4;
				white-space: normal;
				overflow: hidden;
				text-overflow: ellipsis;
				-webkit-line-clamp: 1;
				/* 设置超出多少行隐藏 */
				-webkit-box-orient: vertical;
				display: -webkit-box;
			}

			.goodsPrice {
				height: 60rpx;
				font-size: 40rpx;
				font-weight: bolder;
				color: #ff5500;
			}
		}
	}

	.dataShow {
		margin-top: 30rpx;
		height: 450rpx;
		background: #FFFFFF;

		.dataTitle {
			height: 80rpx;
			line-height: 80rpx;
			padding-left: 20rpx;
			font-weight: bolder;
			border-bottom: 1rpx solid #E6E6E6;
		}

		.minPriceBox {
			height: 40rpx;
			padding-left: 20rpx;
			font-size: 28rpx;
		}
	}

	.nowDataBox {
		width: 400rpx;
		height: 500rpx;
	}
</style>
