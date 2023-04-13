<template>
    <div class="websocket">
        <a-layout-header style="background: rgba(255, 255, 255, 0);">
            <a-typography-title :level="3" style="text-align: left;color:#1da57a; margin-top:20px;">
                多文件异步映射
            </a-typography-title>
            <a-divider style="border-color: #7cb305" dashed />
        </a-layout-header>
        <a-layout-content style="margin:0 50px;font-size: larger;font-weight: 550;" v-if="visiable">
            <br>
            <a-form :model="formws">
                <!-- <a-form-item label="url" >
                    <a-input v-model:value="formws.url"/>
                </a-form-item>
                <a-form-item :wrapper-col="{ offset: 8, span: 16 }" style="position:fixed;right:20%">
                    <a-button type="primary" @click="onWs">Submit</a-button>
                </a-form-item> -->

                <!-- <a-row :gutter="50"> -->
                    
                    <a-form-item style="color:green;width:100%;" label="标准数据源">
                        <a-upload-dragger
                        :file-list="filelist1"
                        name="first"
                        class="first"   
                        @change="handleChange1"
                        @preview="handlePreview"
                        :max-count="1"
                        :show-upload-list="{ showDownloadIcon: true, showRemoveIcon: true }"
                        :customRequest="file=>uploadForm1(file)"
                        accept=".csv"
                        style="background: rgba(255, 255, 255,0.5)"
                        >
                            <div class="btn1" style="color: #1da57a;">
                            <upload-outlined style="color: #1da57a;"></upload-outlined>
                            Upload
                          </div>
                        </a-upload-dragger>
                    </a-form-item>
                <!-- </a-row>     -->
                <!-- <a-row :gutter="50"> -->
                    
                     <a-form-item style="color:green;width:100%" label="异构数据源">
                    <div class="myItem">    
                        <a-upload-dragger
                        :file-list="filelist2"
                        :multiple="true"
                        name="second"
                        class="second"   
                        @change="handleChange2"
                        @drop="handleDrop" 
                        :show-upload-list="{ showDownloadIcon: true, showRemoveIcon: true }"
                        :customRequest="file=>uploadForm2(file)"
                        accept=".csv"
                        style="background: rgba(255, 255, 255,0.5)"
                        >
                        <p class="ant-upload-drag-icon">
                            <inbox-outlined style="color: #1da57a;"></inbox-outlined>
                          </p>
                          <p class="ant-upload-text" style="color: #1da57a;
                          font-weight: lighter;">Click or drag file to this area to upload</p>
                          <!-- <p class="ant-upload-hint">
                            Support for a single or bulk upload. Strictly prohibit from uploading company data or other
                            band files
                          </p> -->
                        </a-upload-dragger>
                    </div>    
                    </a-form-item>   
                    
                    
                <!-- </a-row> -->
                <a-form-item style="left: 10%;" v-show="loading">
                    <div small-bg>
                        <dv-loading>
                          <div color-white>
                            Loading...
                          </div>
                        </dv-loading>
                      </div>
                </a-form-item >
                <a-form-item :wrapper-col="{ offset: 8, span: 16 }" style="position:fixed;">
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
import { LoadingOutlined,UploadOutlined,PlusOutlined,VerticalAlignTopOutlined,InboxOutlined} from '@ant-design/icons-vue';
import axios from 'axios';
import FormData from 'form-data';

export default{
    data(){
        return {
            
            visiable: true,
            
            formws: {
                url: 'ws://localhost:8123/ws/session',
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
            previewTitle: '',
            flag:false,
        }
    },
    components:{
        PlusOutlined,
        UploadOutlined,
        VerticalAlignTopOutlined,
        InboxOutlined,
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
                
                const date = this.formatDate(new Date())
                
                console.log('服务端回应' + date + e.data);
                var t=JSON.parse(e.data)
                // console.log(t.value);

                if (t.type === 'sessionId'){
                    this.formws.sessionid = t.value;
                    console.log('sessionId:',this.formws.sessionid)
                }
                if (t.type === 'file_id'){
                    this.formws.file2.forEach(function (element, index, array) {
                        console.log('flie2:',element)
                    });
                    this.loading = false;
                }

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
        delay(ms) {
        return new Promise((resolve) => setTimeout(resolve, ms));
        },
        async onSubmit() {
            this.onWs();

            await this.delay(2000);

            const form = new FormData()
            form.append('sessionId', this.formws.sessionid)
            form.append('file', this.formws.file1)
            
            this.formws.file2.forEach(function (element, index, array) {
                form.append('file_lists', element)
                // element.status='uploading'
            });
            for (const [key, value] of form.entries()) {
                console.log(`${key}: ${value}`);
                console.log(value);
            }
            this.loading = true;
            // console.log('file:'+form.has('file'))
            // this.visiable=false //测试
            this.$axios.post('http://localhost:8123/mapping/map', form, {
                "Content-Type": "multipart/form-data;charset=utf-8",
                'Host': 'localhost:8123', 
                'Connection': 'keep-alive',
            })
            .then(res=>{
                console.log(res)
                if(res.status==200){
                    console.log(JSON.stringify(res.data));
                    // this.loading = false;
                    // this.flag = true;
                    // this.handleChange2();
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        handleChange1(info) {
            console.log("handleChange1", info.file);
            // if (this.flag) {
            //     info.file.status = 'uploading';
            //     console.log('++++++uploading')
            //     this.flag=false
            // } else {
                info.file.status = 'done';
                // console.log('======done=====')
            // }
            // if (info.file.status === 'uploading') {
            //     console.log('formws.file1 uploading');
            //     this.loading = true;
            // }
        },
        handleChange2(info){
            console.log("handleChange2", info.file.name)
            // if (this.flag) {
            //     info.file.status = 'uploading';
            //     console.log('++++++uploading')
            //     this.flag=false
            // } else {
            //     info.file.status = 'done';
            //     console.log('======done=====')
            // }
            info.file.status = 'done'
            name = info.file.name.replace(/\.csv$/, '');
            info.file.url = 'http://localhost:8123/mapping/download/' + name
            console.log(info.file.url)
            // if(info.file.status==='uploading') {
            //     this.loading = true;
            //     // return; 
            // }
        },

        uploadForm1(filelist1) {
            
            this.formws.file1 = filelist1.file
            console.log(this.formws.file1)
            
        },
        uploadForm2(filelist2) {
            this.formws.file2.push(filelist2.file) 
            console.log(this.formws.file2)
        },
        handleDrop(e) {
        console.log(e);
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
<style lang="less">
.websocket{
    /*.second{
    width: 100%;
    background: rgba(255,255,255,0.6);
    
    }*/
    /*.btn2{
        width:100%;
        background: rgba(255,255,255,0.6);
        height: 300px;
    }*/
    .first,.ant-upload-drag{
    background: rgba(255, 255, 255,0.3);
    }
    .myItem{
        color: red;
        :global{
            .ant-upload-drag{
            color: rgb(1, 200, 97);
            font-weight: lighter;
            }
        }
        
    }

}

</style>