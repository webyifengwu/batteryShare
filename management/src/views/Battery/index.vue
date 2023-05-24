<template>
  <div>
    <!-- 标题处不写 -->
    <a-card>
      <template #title>
        <div>
          <a-select ref="select" v-model:value="statusOption" style="width: 120px" @focus="focus" @change="statusChange">
            <a-select-option value="充电宝状态">充电宝状态</a-select-option>
            <a-select-option value="可租借">可租借</a-select-option>
            <a-select-option value="租出中">租出中</a-select-option>
          </a-select>
          <a-select ref="select" v-model:value="searchOption" style="width: 140px" @focus="focus">
            <a-select-option value="按品牌名搜索">按品牌名搜索</a-select-option>
            <a-select-option value="按投放点名搜索">按投放点名搜索</a-select-option>
          </a-select>

          <a-input-search v-model:value="value" style="width:50%" placeholder="搜索充电宝信息" enter-button="搜索"
            @search="onSearch" />
        </div>
      </template>
      <template #extra>
        <!-- <a href="#">more</a> -->
        <a-button type="primary" @click="addBattery" block>添加充电宝</a-button>
      </template>



      <a-table :data-source="data" :pagination="pagination" @change="changeTable">

        <a-table-column key="key" title="编号" data-index="key" align="center" />
        <a-table-column key="brand" title="充电宝品牌" data-index="brand" align="center" />
        <a-table-column key="total" title="定金/块" data-index="total" align="center" />
        <a-table-column key="price" title="租借的价格/小时" data-index="price" align="center" />
        <a-table-column key="status" title="租借状态" data-index="status" align="center">
          <template #default="{ text: status }">
            <div>
              {{ status ? "可租借" : "租出中" }}
            </div>
          </template>
        </a-table-column>
        <a-table-column key="dpName" title="投放点" data-index="dpName" align="center" />
        <a-table-column key="img" title="图片" data-index="img" align="center">
          <template #default="src">
            <a-image :width="50" :height="50" style="margin-top: -16px;" :preview="true" :src="src.text" />
          </template>
        </a-table-column>

        <a-table-column key="key" title="操作" data-index="key" align="center">
          <template #default="{ text: id }">
            <span>
              <a-tag color="blue" @click="changeItem(id)">
                修改
              </a-tag>
              <a-popconfirm title="确定删除?" ok-text="确定" cancel-text="取消" @confirm="deleteItem(id)" @cancel="cancel">
                <a-tag color="red">删除</a-tag>
              </a-popconfirm>
            </span>
          </template>
        </a-table-column>

      </a-table>

    </a-card>
    <br />

  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { getBattery, deleteBattery, searchBattery } from "@/API/url"
import { useRouter } from "vue-router"
import { uploadUrl } from "@/API/url"
const router = useRouter()
const value = ref('');
const statusOption = ref('充电宝状态')
const searchOption = ref('按品牌名搜索')
// {
//     key: 1,
//     pId: "d90",
//     total: 15,
//     price: 3,
//     status: '1',
//     dpName: "l30",
//     brand: "不知名",
//     img: "http://localhost:8080/img/battery.f703b000.jpg",
//   }

const data = ref([

]);

const pagination = ref({
  current: 1,
  hideOnSinglePage: true,
  pageSize: 10,
  total: 1
})
//修改
const changeItem = (value) => {
  router.push({ name: "addBattery", query: data.value[value] })
}
// 选择
const statusChange = () => {
  pagination.value.current = 1
  getData("init")
}
//搜索
const onSearch = searchValue => {
  pagination.value.current = 1
  getData("search", searchValue)
}
// 删除
const deleteItem = (value) => {
  deleteBattery({ id: data.value[value].pId }).then(() => {
    getData("init")
  })
}
// 添加
const addBattery = () => {
  router.push("addBattery")
}
//分页
let changeTable = (page) => {
  pagination.value = page
  if (value.value === "") {
    getData("init")
  } else {
    getData("search", value.value)
  }
}
//刷新数据
const getData = async (method, Value) => {
  let res = {}
  let status = null
  if (statusOption.value === "可租借") {
    status = 1
  } else if (statusOption.value === "租出中") {
    status = 0
  } else {
    status = null
  }

  if (method === "init") {
    res = await getBattery({
      pageNum: pagination.value.current,
      pageSize: pagination.value.pageSize,
      status: status
    })
  } else if (method === "search") {
    if (searchOption.value === "按品牌名搜索") {
      res = await getBattery({
        pageNum: pagination.value.current,
        pageSize: pagination.value.pageSize,
        status: status,
        brand: Value
      })
    } else {
      res = await getBattery({
        pageNum: pagination.value.current,
        pageSize: pagination.value.pageSize,
        status: status,
        dpName: Value
      })
    }

  }
  if (res.code === 200) {

    pagination.value.total = res.data.total
    data.value = res.data.list.map((item, index) => {
      item.key = index;
      item.img = uploadUrl + item.img
      return item
    })
  }


}

onMounted(() => {
  getData("init")

})
</script>


<style></style>
