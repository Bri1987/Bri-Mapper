<template>
    <div>
        <a-layout-header style="background: rgba(255, 255, 255, 0);">
            <a-typography-title :level="3" style="text-align: left;color:#1da57a">
                多文件异步映射
            </a-typography-title>
            <a-divider style="border-color: #7cb305" dashed />
        </a-layout-header>
        <a-layout-content style="margin:0 50px;font-size: larger;font-weight: 550;" v-if="visiable">
            <br>
            <a-form :model="formws">
                <a-form-item label="url" >
                    <a-input v-model:value="formws.url"/>
                </a-form-item>
                <a-form-item :wrapper-col="{ offset: 8, span: 16 }" style="position:fixed;right:20%">
                    <a-button type="primary" @click="onWs">Submit</a-button>
                </a-form-item>

                <a-row :gutter="48">
                    
                    <a-form-item style="color:green" label="标准数据源">
                        <a-upload
                        :file-list="filelist1"
                        name="first"
                        list-type="picture-card"
                        class="first"   
                        @change="handleChange1"
                        @preview="handlePreview"
                        :max-count="1"
                        :customRequest="file=>uploadForm1(file)"
                        accept=".csv"
                        >
                            <div>
                                <loading-outlined v-if="loading"></loading-outlined>
                                <upload-outlined v-else></upload-outlined>
                                <div class="ant-upload-text">Upload</div>
                            </div>
                            <vertical-align-top-outlined v-if="loading"/>{{ formws.file1.name}}
                        </a-upload>
                        <!-- <a-modal :visible="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
                            <img alt="example" style="width: 100%" :src="previewImage" />
                          </a-modal> -->
                    </a-form-item>
                </a-row>    
                <a-row :gutter="48">
                    <a-form-item style="color:green" label="异构数据源">
                        <a-upload
                        :file-list="filelist2"
                        :multiple="true"
                        name="second"
                        list-type="picture-card"
                        class="second"   
                        @change="handleChange2"
                        :customRequest="file=>uploadForm2(file)"
                        accept=".csv"
                        >
                            <div>
                                <loading-outlined v-if="loading"></loading-outlined>
                                <upload-outlined v-else></upload-outlined>
                                <div class="ant-upload-text">Upload</div>
                            </div>
                        </a-upload>
                        
                    </a-form-item>
                </a-row>
                
                <a-form-item :wrapper-col="{ offset: 8, span: 16 }" style="position:fixed;bottom: 15%;right:20%">
                    <a-button type="primary" @click="onSubmit">Submit</a-button>
                </a-form-item>
            </a-form>
        </a-layout-content>
        <a-layout-content style="position:fixed;left:400px;top:200px;font-size: larger;font-weight: 550;" v-else>
            <a-result
                status="success"
                title="成功实现多文件异步映射!"
                sub-title="Mapping was successfully achieved!"
            ></a-result>  
        </a-layout-content>
    </div>
</template>
<script>
import { LoadingOutlined,UploadOutlined,PlusOutlined,VerticalAlignTopOutlined} from '@ant-design/icons-vue';
import axios from 'axios';
import FormData from 'form-data';

export default{
    data(){
        return {
            
            visiable: true,
            
            formws: {
                url: '',
                sessionid:'',
                file1: {},
                file2:[], 
            },
            
            //     [
            //     {
            //     uid: '',
            //     name: '',
            //     status: 'done',
            //     response: 'Server Error 500', // custom error message to show
            //     url: ''
            //     }
            // ],
            loading: false,
            previewVisible: false,
            previewImage: '',
            previewTitle:'',
        }
    },
    components:{
        PlusOutlined,
        UploadOutlined,
        VerticalAlignTopOutlined,
    },
    methods: {
        formatDate(now)   {    
            var   year=now.getFullYear();     
            var   month=now.getMonth()+1;     
            var   date=now.getDate();     
            var   hour=now.getHours();     
            var   minute=now.getMinutes();     
            var   second=now.getSeconds();     
            return   year+"-"+(month=month<10?("0"+month):month)+"-"+(date=date<10?("0"+date):date)+" "+(hour=hour<10?("0"+hour):hour)+":"+(minute=minute<10?("0"+minute):minute)+":"+(second=second<10?("0"+second):second);     
        } ,
        onWs() {
            console.log(this.formws.url);
            const ws = new WebSocket(this.formws.url);
            console.log(ws);
            ws.addEventListener('open', e => {
                console.log('长连接连接成功')
                // 连接成功后的回调函数，连接成功后一般需要开启心跳方法
                // clearTimeout(timer.reconnectObj)
                // dispatch('wsHeartStart')
            });

            ws.addEventListener('message', e => {
                
                const date =this.formatDate(new Date())
                console.log('服务端回应' + date + e.data);
                this.formws.sessionid = e.data.value;
                // 接收服务端数据时触发的回调函数
                // dispatch('wsHearReset')
                // dispatch('handlerWSMessage', e)
            });

            ws.addEventListener('close', e => {
                console.log('WebSocket已断开')
                // 连接关闭时触发
                // dispatch('handlerWSClose', e)
            });

            ws.addEventListener('error', e => {
                console.log('WebSocket发生错误')
                // 通信发生错误时触发
                // dispatch('handlerWSError', e)
            });
        },
        onSubmit() {

            const form = new FormData()
            form.append('sessionid', this.formws.sessionid)
            form.append('file', this.formws.file1)
            
            this.formws.file2.forEach(function (element, index, array) {
                form.append('file_list', '${ element }');
            });
            for (const [key, value] of form.entries()) {
                console.log(`${key}: ${value}`);
            }
            // console.log('file:'+form.has('file'))
            // this.visiable=false //测试
            this.$axios.post('http://localhost:8123/mapping/map', form, {
                "Content-Type": "multipart/form-data;charset=utf-8"
            })
            .then(res=>{
                console.log(res)
                if(res.status==200){
                    console.log(JSON.stringify(res.data));
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        handleChange1(info) {
            console.log("handleChange1",info.file.name);
            // if (info.file.status === 'uploading') {
            //     console.log('formws.file1 uploading');
            //     this.loading = true;
            // }
            // if (info.file.status === 'done') {
            //     console.log('formws.file1 done');
            //     this.loading = false;
        //    }
        },
        handleChange2(info){
            console.log("handleChange2",info.file.name)
            // if(info.file.status==='uploading') {
            //     this.loading = true;
            //     // return; 
            // }
            if (info.file.status === 'done') {
                console.log('formws.file1 done');
                this.loading = false;
           }
        },

        uploadForm1(filelist1) {
            this.formws.file1 = filelist1.file
            console.log(this.formws.file1)
        },
        uploadForm2(filelist2) {
            this.formws.file2.push(filelist2.file) 
            console.log(this.formws.file2)
        },

        // handleCancel: () => {
        //     this.previewVisible = false;
        //     this.previewTitle = '';
        // },

        // handlePreview: async file => {
        //     if (!file.url && !file.preview) {
        //     file.preview = await getBase64(file.originFileObj);
        // }
        // this.previewImage= file.url || file.preview;
        // this.previewVisible = true;
        // this.previewTitle= file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
        // },
        
    }
}
</script>
<style></style>