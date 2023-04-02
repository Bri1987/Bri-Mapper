<template>
    <div>
        <div class="greycover" v-show="grey"></div>
        <a-card title="详细信息" :bordered="false" class="details" v-show="isshowdetails">
            <close-circle-outlined class="exit" @click="exit_details"/>
            <a-list>
                <a-list-item v-for="(value,key,index) in detailInfo" :key="index">
                    {{ key }} : {{ value }}
                </a-list-item>
            </a-list>
        </a-card>
        <a-layout>
            <a-input-search
                v-model:value="select_sql"
                placeholder="input select_sql"
                enter-button="Search"
                size="large"
                @search="onSearch" 
                bordered="false"
                />
            <a-layout-content style="position:absolute;height: 70%;top:22%;bottom: 0%;width: 72%;overflow: hidden;">
                <div v-show="visiable" style="position: absolute;height:100%;left:0;right: -17px;top:0;bottom: 0;overflow-x: hidden;overflow-y: scroll;">
                <a-row :gutter="[24,48]" style="margin-bottom: 32px;" >
                    <a-col :span="8" v-for="(data,index) in datas" :key="index">
                        <a-card class="wholecards" :bordered="false" hoverable="true" @click="showdetails(data)">
                            <template #title>
                                <heat-map-outlined />
                                {{ data.科技平台服务资源名称 }}
                            </template>
                            <p>内容描述: {{ data.科技平台服务内容描述 }}</p>
                            <p>资源标识代码: {{ data.科技平台服务资源标识代码 }}</p>
                            <p>最近发布日期:</p><p>{{data.科技平台服务最近发布日期 }}</p>
                        </a-card>
                    </a-col>
                </a-row>
                </div>
            </a-layout-content>
        </a-layout>
    </div>
</template>
<script>
import axios from 'axios';
import {HeatMapOutlined, CloseCircleOutlined} from '@ant-design/icons-vue'
export default{
    components:{
        HeatMapOutlined, CloseCircleOutlined
    },
    data(){
        return {
            select_sql:'',
            datas:[],
            // datas:[
            //         {
            //             "科技平台服务最近发布日期": "2021-10-24T16:00:00.000+00:00",
            //             "科技平台服务资源标识代码": "gl",
            //             "科技平台服务资源名称": "工作室",
            //             "科技平台服务服务方信息": 1,
            //             "科技平台服务内容描述": "闪闪发亮的科技平台",
            //             "科技平台服务访问限制": 0,
            //             "科技平台服务关键词": null,
            //             "科技平台服务名称": null,
            //             "科技平台服务分类": null
            //         },
            //         {
            //             "科技平台服务最近发布日期": "2022-10-24T16:00:00.000+00:00",
            //             "科技平台服务资源标识代码": "bri",
            //             "科技平台服务资源名称": "欧拉1",
            //             "科技平台服务服务方信息": 1,
            //             "科技平台服务内容描述": "中国最好的科技平台",
            //             "科技平台服务访问限制": 1,
            //             "科技平台服务关键词": null,
            //             "科技平台服务名称": null,
            //             "科技平台服务分类": null
            //         },
            //         {
            //             "科技平台服务最近发布日期": "2022-10-24T16:00:00.000+00:00",
            //             "科技平台服务资源标识代码": "bri",
            //             "科技平台服务资源名称": "欧拉2",
            //             "科技平台服务服务方信息": 1,
            //             "科技平台服务内容描述": "中国最好的科技平台",
            //             "科技平台服务访问限制": 1,
            //             "科技平台服务关键词": null,
            //             "科技平台服务名称": null,
            //             "科技平台服务分类": null
            //         },
            //         {
            //             "科技平台服务最近发布日期": "2022-10-24T16:00:00.000+00:00",
            //             "科技平台服务资源标识代码": "bri",
            //             "科技平台服务资源名称": "欧拉3",
            //             "科技平台服务服务方信息": 1,
            //             "科技平台服务内容描述": "中国最好的科技平台",
            //             "科技平台服务访问限制": 1,
            //             "科技平台服务关键词": null,
            //             "科技平台服务名称": null,
            //             "科技平台服务分类": null
            //         },
            //         {
            //             "科技平台服务最近发布日期": "2021-10-24T16:00:00.000+00:00",
            //             "科技平台服务资源标识代码": "gl",
            //             "科技平台服务资源名称": "工作室",
            //             "科技平台服务服务方信息": 1,
            //             "科技平台服务内容描述": "闪闪发亮的科技平台",
            //             "科技平台服务访问限制": 0,
            //             "科技平台服务关键词": null,
            //             "科技平台服务名称": null,
            //             "科技平台服务分类": null
            //         },
            //         {
            //             "科技平台服务最近发布日期": "2022-10-24T16:00:00.000+00:00",
            //             "科技平台服务资源标识代码": "bri",
            //             "科技平台服务资源名称": "欧拉11",
            //             "科技平台服务服务方信息": 1,
            //             "科技平台服务内容描述": "中国最好的科技平台",
            //             "科技平台服务访问限制": 1,
            //             "科技平台服务关键词": null,
            //             "科技平台服务名称": null,
            //             "科技平台服务分类": null
            //         },
            //         {
            //             "科技平台服务最近发布日期": "2022-10-24T16:00:00.000+00:00",
            //             "科技平台服务资源标识代码": "bri",
            //             "科技平台服务资源名称": "欧拉12",
            //             "科技平台服务服务方信息": 1,
            //             "科技平台服务内容描述": "中国最好的科技平台",
            //             "科技平台服务访问限制": 1,
            //             "科技平台服务关键词": null,
            //             "科技平台服务名称": null,
            //             "科技平台服务分类": null
            //         },
            //         {
            //             "科技平台服务最近发布日期": "2022-10-24T16:00:00.000+00:00",
            //             "科技平台服务资源标识代码": "bri",
            //             "科技平台服务资源名称": "欧拉13",
            //             "科技平台服务服务方信息": 1,
            //             "科技平台服务内容描述": "中国最好的科技平台",
            //             "科技平台服务访问限制": 1,
            //             "科技平台服务关键词": null,
            //             "科技平台服务名称": null,
            //             "科技平台服务分类": null
            //         }
            //     ],
            // length:0,
            // rowcount:0,
            visiable:false,
            grey:false,
            detailInfo:{},
            isshowdetails:false
        }
    },
    methods:{
        onSearch(){
            var form1 = new FormData()
            form1.append('select_sql',this.select_sql)
            console.log(form1)
            this.$axios.post('http://localhost:8121/function/select',form1)
            .then(res=>{
                console.log(res)
                if(res.status==200)
                {
                    //展示小卡片
                    this.datas=res.data.data
                    // var length=this.datas.length
                    
                    this.showCards()
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        showCards(){
            this.visiable=true
        },
        showdetails(data){
            console.log(data)
            this.grey=true
            this.isshowdetails=true
            this.detailInfo=data
        },
        exit_details(){
            this.isshowdetails=false
            this.grey=false
            this.detailInfo={}
        }
    }
}
</script>
<style scoped>
.cards_holder{
    background-color: #fff;
    height: 100%;
    position: inherit;
    top:0%;
    bottom: 0%;

}
p{
    text-align: left;
    color: #547116;
}
.greycover{
    position: fixed;
    height: 100%;
    width: 100%;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background: rgba(187, 187, 187, 0.5);
    z-index: 9;
}
.details{
    z-index: 999;
    width: 600px;
    position: fixed;
    top:19%;
    left:36%
}
.exit{
    position: absolute;
    height: 2em;
    width: 2em;
    right: 25px;
    top:25px
}
.exit:hover{
   color: green;
   font-size: larger;
}
.wholecards{
    border-radius: 5%;
}
.wholecards:hover{
    box-shadow: 0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23);
}
</style>