<template>
  <div>
    <!-- 标题处不写 -->
    <a-card>
      <template #title>
        <!-- <a href="#">more</a> -->
        <div>
          <a-button type="primary" block style="width: 100px;" @click="showModal">添加角色</a-button>
          <a-modal v-model:visible="visible" title="添加角色"  okText="确定" cancelText="取消" @ok="handleOk">
            角色名：<a-input v-model:value="roleName"></a-input>
            角色权限：
            <a-select v-model="roleId" style="width: 120px">
              <a-select-option value="0">普通用户</a-select-option>
              <a-select-option value="1">超级用户</a-select-option>
            </a-select>
          </a-modal>
        </div>
      </template>


      <a-table :data-source="data" :pagination="false">

        <a-table-column key="roleId" title="id" data-index="roleId" align="center" />
        <a-table-column key="roleName" title="角色身份" data-index="roleName" align="center" />


        <a-table-column key="key" title="操作" data-index="key" align="center">
          <template #default="{ text: id }">
            <span>
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
import { ref } from 'vue';
import { useRouter } from "vue-router"
const router = useRouter()
const roleName = ref("")
const roleId = ref(0)
const data = [
  {
    key: "1",
    roleId: 1,
    roleName: "管理员"
  }, {
    key: "0",
    roleId: 0,
    roleName: "普通用户"
  }
];
const visible = ref(false);
const showModal = () => {
  visible.value = true;
};
const handleOk = () => {
  visible.value = false;
};

const deleteItem = (value) => {
}

</script>


<style></style>
