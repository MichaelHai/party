// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import VueRouter from "vue-router"
import ElementUI from 'element-ui'
import VueResource from 'vue-resource'
import 'element-ui/lib/theme-default/index.css'

Vue.use(VueRouter);
Vue.use(ElementUI);
Vue.use(VueResource);

import Main from "pages/Main";
import GanDengYan from "pages/GanDengYan";
import About from "pages/About";
import NotFound from "pages/404";

const routes = [
    {path: '/', component: Main},
    {path: '/GanDengYan', component: GanDengYan},
    {path: '/About', component: About},
    {path: '*', component: NotFound}
];

const router = new VueRouter({
    mode: "history",
    routes
});

import App from "./ui/App";
const app = new Vue({
    render: h => h(App),
    router
}).$mount("#app");