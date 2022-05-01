<template>
	<view>
		<!-- 顶部 -->
		<view class="nav">
			<view class="titleBox">
				<text class="title">朴朴商品数据查询</text>
			</view>
			<view class="searchBox">
				<!-- <i class="search iconfont icon-sousuo">请输入商品名称</i> -->
				<u-search bgColor="#FFFFFF" :showAction="false" placeholder="请输入商品名称" v-model="searInfo" shape="square" height="76rpx" searchIconSize="60rpx" @change="change()"></u-search>
			</view>
		</view>
		<view style="height: 260rpx;"></view>
		<!-- 商品内容 -->
		<view class="content">
			<view v-for="(goods,index) in goodsList" :key="index">
				<view class="goodsCard" @tap="goodsInfo(goods.uuid)">
					<!-- 商品图片 -->
					<view class="goodsImgBox">
						<image :src="goods.img" class="goodsImg"></image>
					</view>
					<view style="padding: 15rpx;">
						<view class="goodsNameBox">{{goods.name}}</view>
						<view class="goodsSubTitle">{{goods.subTitle}}</view>
						<view class="goodsInfo">
							<text class="goodsPrice">{{goods.price}}</text>
							<text class="goodsOrigin">{{goods.origin}}</text>
						</view>
					</view>
				</view>
			</view>
			<view class="tips" style="height: 20rpx;width: 100%;clear: both;"></view>
		</view>
		<u-tabbar class="tabbar" :value="tabbarValue" :fixed="false" :placeholder="false" :safeAreaInsetBottom="false">
			<u-tabbar-item text="首页" icon="home" @click="tabbarClick"></u-tabbar-item>
			<u-tabbar-item text="数据采集" icon="photo" @click="tabbarClick"></u-tabbar-item>
		</u-tabbar>


	</view>
</template>
<script>
	export default {
		data() {
			return {
				searInfo:'',
				tabbarValue: 0,
				//商品列表
				goodsList: []
			}
		},
		onLoad() {
			this.getDataList()
			console.log(this.goodsList)
		},
		methods: {
			goodsInfo(goodsID) {
				console.log(goodsID)
				uni.redirectTo({
					url: '../goods/goods?goodsID=' + goodsID
				})
			},
			tabbarClick(e) {
				if (e == 1) {
					uni.redirectTo({
						url: '../getData/getData'
					})
				}
			},
			getDataList() {
				var self = this
				uni.request({
					url: '/api/getData/getList',
					success(res) {
						console.log(res)
						self.goodsList = res.data.data
					}
				})
			},
			change(){
				console.log(this.searInfo)
				var self=this
				uni.request({
					url:'/api/getData/search?name='+self.searInfo,
					success(res) {
						self.goodsList = res.data.data
					}
				})
			}
		}
	}
</script>
<style lang="scss">
	* {
		padding: 0;
		margin: 0;
	}

	.nav {
		position: fixed;
		z-index: 1;
		top: 0;
		left: 0;
		height: 250rpx;
		width: 100%;
		background-color: rgb(12, 55, 97);
		padding-top: 30rpx;
		box-sizing: border-box;

		.titleBox {
			width: 60%;
			margin: 0 auto;
			border-bottom: 10rpx solid #ff5500;
			text-align: center;

			.title {
				font-size: 50rpx;
				font-weight: bold;
				color: #FFFFFF;
			}

		}

		.searchBox {
			margin: 0 auto;
			margin-top: 30rpx;
			width: 90%;
			height: 80rpx;
			line-height: 80rpx;
			background-color: #FFFFFF;
			border-radius: 5rpx;
			text-align: center;

			.search {
				color: #a0a0a0;
				font-size: 35rpx;
			}
		}
	}

	.content {
		padding-left: 10rpx;
		padding-right: 10rpx;

		// background-color: rgb(245,245,245);
		.goodsCard {
			border: 1rpx solid rgb(225, 225, 225);
			border-radius: 10rpx;
			box-sizing: border-box;
			background-color: #FFFFFF;
			width: 48%;
			margin: 1%;
			float: left;

			.goodsImgBox {
				width: 100%;
				height: 340rpx;
				margin: 0 auto;

				.goodsImg {
					width: 100%;
					height: 100%;
				}
			}

			.goodsNameBox {
				width: 100%;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
				font-size: 30rpx;
				font-weight: bold;
			}

			.goodsSubTitle {
				margin: 10rpx 0;
				font-size: 20rpx;
				color: #787878;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
			}

			.goodsInfo {
				height: 60rpx;
				line-height: 60rpx;

				.goodsPrice {
					font-size: 40rpx;
					font-weight: bolder;
					color: #ff5500;
				}

				.goodsOrigin {
					float: right;
					font-size: 20rpx;
					color: #787878;
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
					width: 160rpx ;
					text-align: right;
				}
			}

		}
	}

	.tabbar {
		position: fixed;
		width: 100%;
		bottom: 0;
		border-top: 1rpx solid #f5f5f5;
	}
</style>
