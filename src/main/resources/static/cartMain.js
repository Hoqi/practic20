
var tempUserId = 1;
var itemsApi = Vue.resource('users{/id}/cart')
var incApi = Vue.resource('users{/id}/cart/add{/vendorCode}')
var decApi = Vue.resource('users{/id}/cart/delete{/vendorCode}')


Vue.component('item',{
    data: function () {
        return {
            vendorCode: this.item.product.vendorCode,
            count : this.item.count,
            fullPrice : this.item.product.price * this.item.count,
            price : this.item.product.price
        }
    },
    props: ['item'],
    template: '<tr>' +
        '<td><strong>{{item.product.name}}</strong></td>' +
        '<td><strong>{{this.fullPrice}}</strong></td>' +
        '<td><strong>{{this.count}}</strong></td>' +
        '<input type="button" value="-" v-on:click="dec">' +
        '<input type="button" value="+" v-on:click="inc">' +
        '</tr>',
    methods: {
        inc: function () {
            incApi.save({id: tempUserId, vendorCode: this.vendorCode},'add').then(response => {
                this.count++;
                this.fullPrice += this.price;
            },reason => {
                console.log(reason);
            })
        },
        dec: function () {
            decApi.save({id: tempUserId, vendorCode: this.vendorCode},'delete').then(response => {
                if (this.count > 1){
                    this.count--;
                    this.fullPrice -= this.price;
                }
                else {

                }
            })
        }

    }
})


Vue.component('items-list',{
    props: ['items'],
    template: '<table>' +
        '<item v-for="item in items" v-bind:key="item.id" v-bind:item="item"/>' +
        '</table>'
})

var app = new Vue({
    el: '#app',
    template: '<items-list :items="cart.shopCartItems" />',
    data: {
        cart: [],
    },
    created: function () {
        itemsApi.get({id: tempUserId}).then(result =>
            result.json().then(data =>
               this.cart = data
                //console.log(data)
            )
            )
    }
})