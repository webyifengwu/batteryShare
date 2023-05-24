<template>
    <div>
        <a-switch :checked="theme === 'dark'" checked-children="Dark" un-checked-children="Light" @change="changeTheme" />
        <a-menu v-model:openKeys="openKeys" v-model:selectedKeys="selectedKeys" style="width: 258px; margin-top: 40px;" mode="inline"
            :theme="theme">
            <router-link :to="{ name: `page` }">
                <a-menu-item key="1">
                    <template #icon>
                        <HomeOutlined />
                    </template>
                    首页
                </a-menu-item>
            </router-link>

            <router-link :to="{ name: `location` }">
                <a-menu-item key="2">
                    <template #icon>
                        <TeamOutlined />
                    </template>
                    充电宝投放点管理
                </a-menu-item>
            </router-link>

            <router-link :to="{name:`battery`}">
                <a-menu-item key="3">
                    <template #icon>
                        <!-- <MailOutlined /> -->
                        <PoweroffOutlined />
                    </template>
                    充电宝管理
                </a-menu-item>
            </router-link>

            <router-link :to="{ name: `pay` }">
                <a-menu-item key="4">
                    <template #icon>
                        <!-- <MailOutlined /> -->
                        <MessageOutlined />
                    </template>
                    订单管理
                </a-menu-item>
            </router-link>

            <a-sub-menu key="sub1">
                <template #icon>
                    <UserOutlined />
                </template>
                <template #title>用户</template>
                <RouterLink :to="{ name: `role` }">
                    <a-menu-item key="5">角色管理</a-menu-item>
                </RouterLink>
                <RouterLink :to="{ name: `user` }">
                    <a-menu-item key="6">用户管理</a-menu-item>
                </RouterLink>
            </a-sub-menu>

        </a-menu>
    </div>
</template>
<script setup>
import { toRefs, ref } from 'vue';
import { HomeOutlined, MessageOutlined, PoweroffOutlined, UserOutlined, TeamOutlined, AppstoreOutlined, SettingOutlined } from '@ant-design/icons-vue';

let theme = ref('dark')
let selectedKeys = ref(['1'])
let openKeys = ref(['sub1'])

const changeTheme = (checked) => {
    theme.value = checked ? 'dark' : 'light';
}

</script>