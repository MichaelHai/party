<template>
    <header>
        <div class="page-container">
            <el-menu mode="horizontal" default-active="/" theme="dark" :router="true" :default-active="$route.path">
                <el-menu-item index="/" class="menu-item">主页</el-menu-item>
                <el-submenu index="" class="submenu">
                    <template slot="title">游戏</template>
                    <el-menu-item index="/GanDengYan" class="submenu-item">干瞪眼</el-menu-item>
                    <el-menu-item index="/TexasHoldemPoker" class="submenu-item">德州</el-menu-item>
                </el-submenu>
                <el-menu-item index="/About" class="menu-item">关于</el-menu-item>
            </el-menu>
        </div>
    </header>
</template>

<script>

</script>

<style>
    header {
        background-color: #324157;
    }

    .menu-item, .el-submenu__title {
        font-size: 18px;
        width: 82px;
        text-align: center;
    }

    .menu-item.is-active, .el-submenu.is-active>.el-submenu__title {
        font-size: 20px;
        font-weight: bold;
        border-bottom-color: #20A0FF;
    }
</style>