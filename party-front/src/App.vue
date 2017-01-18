<template>
    <div id="app">
        <PageHeader></PageHeader>
        <div id="main" class="page-container">
            <router-view></router-view>
        </div>
        <PageFooter></PageFooter>
    </div>
</template>

<script>
    import PageHeader from "components/PageHeader"
    import PageFooter from "components/PageFooter"

    export default {
        name: 'app',
        components: {
            PageHeader,
            PageFooter
        }
    }
</script>

<style>
    html, body {
        margin: 0;
        padding: 0;
        height: 100%;
    }

    #main {
        box-sizing: border-box;
        min-height: 800px;
    }

    .page-container {
        width: 1000px;
        margin: auto;
    }
</style>
