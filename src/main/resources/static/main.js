
var tempUserId = 1;
var productApi = Vue.resource('/products')
var cartApi = Vue.resource('users{/id}/cart/add{/vendorCode}')
var createCartApi = Vue.resource('users{/id}/cart/create')

Vue.component('product-row',{
    data: function() {
        return {
            vendorCode: this.product.vendorCode
        }
    },
    props: ['product'],
    template: '<tr> <td><strong>{{product.name}}</strong></td>  <td>{{product.description}}</td>' +
        '<td><input style="margin-left: 50px" type="button" value="В корзину" v-on:click="add"></td></tr>',
    methods: {
        add: function () {
            createCartApi.save({id: tempUserId},'create').then(response => {
                cartApi.save({id: tempUserId, vendorCode: this.vendorCode},'add').then(response => {
                    console.log(response)
                },reason => {
                    cartApi.save({id: tempUserId, vendorCode: this.vendorCode},'add').then(response => {
                        console.log(response)
                })
            })
        })
        },


    }

})

Vue.component('products-list', {
    props: ['products'],
    template: '<table>' +
                    '<product-row v-for="product in products" v-bind:key="product.id" v-bind:product="product" />' +
              '</table>',
});

var app = new Vue({
    el: '#app',
    template: '<products-list :products="products" />',
    data: {
        products: []
    },
    created: function () {
        productApi.get().then(result =>
            result.json().then(data =>
                data.forEach(product => this.products.push(product))
            )
        )

    }
});
/*
var sumbitForm = new Vue({
   el: '#submit',
   template: `<input type="text" v-model:>`,
   data: {
       address: ''

   }
})
*/
