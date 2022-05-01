<template>
	<view style="overflow:hidden;">
		<u-navbar class="nav" title="朴朴数据实时获取" height="80rpx" bgColor="rgb(12,55,97)"
			titleStyle="color:#fff;font-weight:bolder;font-size:40rpx" leftIconColor="rgb(12,55,97)"></u-navbar>
		<view style="height: 100rpx;">

		</view>
		<view class="searchBox">
			<view style="height:80rpx;width: 40%;float: left;" @click="showType = true">
				<u--input style="height: 100%;border: 1rpx solid #E6E6E6;" labelAlign="center" placeholder="请选择分类(默认蔬菜)"
					disabled disabledColor="#ffffff" border="none" v-model="typeName"></u--input>
			</view>
			<view
				style="height:80rpx;width:60%;text-align: right;padding-left: 40rpx;line-height: 80rpx;box-sizing: border-box;float: right;">
				数据量：
				<u-number-box :max="20" style="height: 100%;float: right;" v-model="count" @change="valChange"
					buttonSize="80rpx" inputWidth="80rpx"></u-number-box>
			</view>
		</view>
		<view class="searBtn" style="padding:0 20rpx;margin-top:30rpx ;">
			<u-button type="primary" text="搜索" @click="getData()"></u-button>
		</view>
		<view class="dataShow" style="height: 800rpx;">
			<u--textarea id="scroll_text" class="textBox" v-model="res" placeholder="请输入内容" style="background:none;" height="780rpx" maxlength="-1" disabled   >
			</u--textarea>
		</view>






		<u-tabbar class="tabbar" :value="tabbarValue" :fixed="false" :placeholder="false" :safeAreaInsetBottom="false">
			<u-tabbar-item text="首页" icon="home" @click="tabbarClick"></u-tabbar-item>
			<u-tabbar-item text="数据采集" icon="photo" @click="tabbarClick"></u-tabbar-item>
		</u-tabbar>


		<u-action-sheet :show="showType" :actions="type" title="请选择类型" @close="showType = false" @select="typeSelect">
		</u-action-sheet>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				screenHeight:'',
				res: '',
				showType: false,
				tabbarValue: 1,
				count: 10,
				searType: 110,
				searSize: 10,
				searUrl: 'https://j1.pupuapi.com/client/marketing/storeproduct/v2/search?business=scenes&store_id=7c1208da-907a-4391-9901-35a60096a3f9&size=10&business_id=110',
				typeName: '蔬菜',
				type: [{
						name: '蔬菜',
						typeID: 110
					},
					{
						name: '水果',
						typeID: 100
					},
					{
						name: '肉禽蛋',
						typeID: 120
					},
					{
						name: '海鲜水产',
						typeID: 130
					},
					{
						name: '粮油调味',
						typeID: 400
					}
				],
				getDataInfo: []
			}
		},
		onReady() {
			this.screenHeight = uni.getSystemInfoSync().windowHeight*2-465
			console.log(this.screenHeight)
		},
		methods: {
			tabbarClick(e) {
				if (e == 0) {
					uni.redirectTo({
						url: '../index/index'
					})
				}
			},
			valChange(e) {
				this.searUrl =
					'https://j1.pupuapi.com/client/marketing/storeproduct/v2/search?business=scenes&store_id=7c1208da-907a-4391-9901-35a60096a3f9&size=' +
					e.value + '&business_id=' + this.searType

			},
			typeSelect(e) {
				this.typeName = e.name
				this.searUrl =
					'https://j1.pupuapi.com/client/marketing/storeproduct/v2/search?business=scenes&store_id=7c1208da-907a-4391-9901-35a60096a3f9&size=' +
					this.searSize + '&business_id=' + e.typeID
			},
			getData() {
				var self = this
				this.res = ''
				this.res += "=======朴朴数据开始采集=======\r"
				this.res += "当前时间：" + new Date().toLocaleDateString() + "\r采集类型:" + this.typeName + "\r采集数据量：" + this
					.count + "\r"
				this.res += "=========================\r"
				uni.request({
					url: this.searUrl,
					success(res) {
						self.getDataInfo=res.data.data.products
						var data = res.data.data.products
						for (var index in data) {
							var item={
								uuid: data[index].product_id,
								name: data[index].name,
								subTitle: data[index].sub_title,
								origin: data[index].origin,
								img: 'https://imgs.pupuapi.com/' + data[index].main_image,
								price: data[index].price / 100
							}
							// self.res+='采集成功\r商品名称为：'+item.name+"\r商品价格为："+item.price+"\r==================\r"
							var infoRes='采集成功\r商品名称为：'+item.name+"\r商品价格为："+item.price+"\r"
							self.saveData(item,infoRes)
						}
					}
				})
			},
			saveData(item,info){
				var self=this
				uni.request({
					url:'/api/getData/saveData',
					data:item,
					method:'POST',
					success(saveRes) {
						// console.log(item)
						console.log(saveRes)
						self.res+=info
						self.res+=saveRes.data.msg+"\r"
						self.res+="==================\r"
					}
				})
			}
		}
	}
</script>
<style lang="scss">
	.searchBox {
		padding: 10rpx 20rpx;
		box-sizing: border-box;
		height: 80rpx;
	}

	.tabbar {
		position: fixed;
		width: 100%;
		bottom: 0;
		border-top: 1rpx solid #f5f5f5;

	}

	.dataShow {
		background-color: #E6E6E6;
		margin: 20rpx;
		padding: 20rpx;
		border-radius: 40rpx;
		.textBox{
			height: 100%;
		}
	}
</style>
