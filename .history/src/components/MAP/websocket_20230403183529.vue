<template>
	<el-upload ref="upload" action="#" multiple :file-list="fileList" :on-change="fileOnChange" :auto-upload="false">
		<el-button type="primary">上传数据源</el-button>
	</el-upload>
	<el-button type="primary" @click="confirm">确定</el-button>
	<el-button type="success" @click="download">下载</el-button>
</template>
 
<script setup>
	import {
		reactive
	} from 'vue'
	import axios from 'axios'
 
 
	const fileList = reactive([])
	const formData = new FormData()
 
	const fileOnChange = (file) => {
 
		//下面部分可以对文件进行判断
		// const isIMAGE = (file.raw.type === 'image/jpeg' || file.raw.type === 'image/png' || file.raw.type ===
		// 	'image/gif');
		// const isLt1M = file.size / 1024 / 1024 < 1;
 
		// if (!isIMAGE) {
		// 	alert('上传文件只能是图片格式!');
		// 	return false;
		// }
		// if (!isLt1M) {
		// 	alert('上传文件大小不能超过 1MB!');
		// 	return false;
		// }
		var reader = new FileReader();
		reader.readAsDataURL(file.raw);
		reader.onload = function(e) {
			console.log(e.currentTarget.result) //图片的base64数据
			//str = str.replace(/^data:image\/\w+;base64,/, "")
		}
 
 
		if (file.status === 'ready') {
			console.log(1)
			fileList.push(file)
		}
	}
 
	//内置地址
	let path = `C:\\Users\\Administrator\\Desktop\\图片\\声音`
 
	const download = () => {
		console.log(2)
		window.location.href = `https://localhost:7065/api/File/action/Download?subDirectory=${path}`
		// axios.get(`https://localhost:7065/api/File/action/Download?subDirectory=${path}`).then((res) => {
		// 	console.log(res)
		// 	if (res.status === 200) {
		// 		console.log(res.data.size)
		// 	}
		// })
	}
	const confirm = () => {
		console.log(formData.has('formFiles'))
 
		fileList.forEach((item, index) => {
            formData.append("formFiles", item.raw)
            console.log(formData)
			//formData.append("subDirectory", 'file')
			console.log(item + index)
			console.log(2)
		})
		console.log(formData.has('formFiles'))
		uploadFiles(formData)
	}
 
	function uploadFiles(data) {
		axios.post("https://localhost:7065/api/File/action/Upload?subDirectory=1", data).then((res) => {
			console.log(res)
			if (res.status === 200) {
				console.log(res.data.size)
			}
		})
	}
</script>