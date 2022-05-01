const BASE_URL = 'http://localhost:8081'
export const request = (options) => {
	return new Promise((resolve, reject) => {
		// #ifdef MP-WEIXIN
		options.url = BASE_URL + options.url
		// #endif
		// #ifdef H5
		options.url = '/api' + options.url
		// #endif
		uni.request({
			url: options.url,
			method: options.method || 'GET',
			data: options.data || {},
			header: options.header,
			success: (response) => {
				const res = response
				resolve(res)
			},
			fail: (error) => {
				reject(error)
			}
		})
	})
}
