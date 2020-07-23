var userApi = Vue.resource('/users')
var createUserApi = Vue.resource('/users/create')
var productApi = Vue.resource('/products')
var cartApi = Vue.resource('users{/id}/cart/add{/vendorCode}')
var createCartApi = Vue.resource('users{/id}/cart/create')
var itemsApi = Vue.resource('users{/id}/cart')
var incApi = Vue.resource('users{/id}/cart/add{/vendorCode}')
var decApi = Vue.resource('users{/id}/cart/delete{/vendorCode}')
var ordersApi = Vue.resource('users{/id}/orders')
var orderSubmitApi = Vue.resource('users{/id}/cart/submit')


Vue.component('item', {
    props: {
        item: null,
        refreshFn: null
    },
    template: '<tr>' +
        '<td><strong>{{item.product.name}}</strong></td>' +
        '<td>{{item.price * item.count}}</td>' +
        '<td>{{item.count}}</td>' +
        '<td><input type="button" value="-" v-on:click="dec">' +
        '<input type="button" value="+" v-on:click="inc"></td>' +
        '</tr>',
    methods: {
        inc: function () {
            incApi.save({
                id: this.$root.userId,
                vendorCode: this.$props.item.product.vendorCode
            }, 'add').then(response => {
                response.json().then(data => {
                    this.refreshFn(data.shopCartItems);
                })
            }, reason => {
                console.log(reason);
            })
        },
        dec: function () {
            console.log(this.item.product.vendorCode)
            decApi.save({
                id: this.$root.userId,
                vendorCode: this.$props.item.product.vendorCode
            }, 'delete').then(response => {
                response.json().then(data => {
                    this.refreshFn(data.shopCartItems);
                })
            }, reason => {
                console.log(reason);
            })
        }

    }
})

Vue.component('cart', {
    data: function () {
        return {
            items: [],
        }
    },
    template: '<div>' +
        '<table border="1">' +
        '<tr v-if="items.length">' +
        '<th>Name</th>' +
        '<th>Price</th>' +
        '<th>Count</th>' +
        '<th>Change</th>' +
        '</tr>' +
        '<item v-for="item in items" v-bind:key="item.id" v-bind:item="item" v-bind:refreshFn="refreshItems"/>' +
        '</table>' +
        '<order-form v-if="items.length" style="margin-top: 50px"/>' +
        '</div>',
    created: function () {
        itemsApi.get({id: this.$root.userId}).then(result =>
            result.json().then(data => {
                    data.shopCartItems.forEach(item => this.items.push(item))
                    // console.log(data);
                }
            )
        )
    },
    methods: {
        refreshItems: function (newItems) {
            this.items = []
            console.log(this.items)
            newItems.forEach(item => this.items.push(item))
            console.log(this.items)
        }
    }
})


Vue.component('product', {
    data: function () {
        return {
            vendorCode: this.product.vendorCode,
        }
    },
    props: ['product'],
    template: '<tr> <td><strong>{{product.name}}</strong></td>  <td>{{product.description}}</td>' +
        '<td><input type="button" value="To cart" v-on:click="add"></td></tr>',
    methods: {
        add: function () {
            cartApi.save({id: this.$root.userId, vendorCode: this.vendorCode}, 'add').then(response => {
                console.log(response)
            })
        },
    }

})

Vue.component('products', {
    data: function () {
        return {
            products: [],
            user: this.$root.userId
        }
    },
    template: '<table border="1">' +
        '<tr v-if="products.length">' +
        '<th>Name</th>' +
        '<th>Description</th>' +
        '<th>Add</th>' +
        '</tr>' +
        '<product v-for="product in products" v-bind:key="product.vendorCode" v-bind:product="product"  />' +
        '</table>',
    created: function () {
        productApi.get().then(result =>
            result.json().then(data =>
                data.forEach(product => this.products.push(product))
            )
        )
    }
});


Vue.component('title-block', {
    props: ['title'],
    template: `<div>     
                    <h3>{{title}}</h3>                 
               </div>`
})


Vue.component('login', {
    data: function () {
        return {
            userId: 1,
            email: ''
        }
    },
    template: `<div>
                    <input type="text" v-model="email" placeholder="Email"/>
                    <input type="button" value="Login" @click="submit"/>
               </div>`,
    methods: {
        submit: function () {
            userApi.get({email: this.email}, '').then(response => {
                response.json().then(data => this.$root.login(data.id))
            }, reason => console.log('NotFound'))
        }
    }
})

Vue.component('order-about', {
    props: ['order', 'changeFn'],
    template: `<div>
                    <div>
                        <span>Address: {{order.address}}</span>
                    </div>
                    <div>
                        <span>Receipt method: {{order.productionMethod}}</span>
                    </div>
                    <div>
                        <span>Payment method: {{order.paymentMethod}}</span>
                    </div>
                    <div>
                        <table border="1">
                            <th><strong>Name</strong></th>
                            <th><strong>Price</strong></th>
                            <th><strong>Count</strong></th>
                            <tr v-for="item in order.shopCartItems">
                            <td>{{item.product.name}}</td>
                            <td>{{item.product.price * item.count}}</td>
                            <td>{{item.count}}</td>
                            </tr>
                        </table>
                    </div>
                    <div><input type="button" value="Назад" @click="changeFn(null)"></div>
               </div>`
})

Vue.component('order-form', {
    data: function () {
        return {
            paymentMethod: '',
            productionMethod: '',
            address: '',
        }
    },
    template: `<div>
                    <div><span>Заказать</span></div>
                    <div><input type="text" v-model="address" placeholder="Address"></div>
                    <div><input type="text" v-model="productionMethod" placeholder="Receipt Method"></div>
                    <div><input type="text" v-model="paymentMethod" placeholder="Payment Method"></div>
                    <div><input type="button" value="Submit" @click="submit"></div>
               </div>`,
    methods: {
        submit: function () {
            let order = {
                address: this.address,
                productionMethod: this.productionMethod,
                paymentMethod: this.paymentMethod
            };
            orderSubmitApi.save({id: this.$root.userId}, order).then(response => {
                alert('Order successfully completed!');
                createCartApi.save({id: this.$root.userId}, 'create').then(response => {
                    this.$root.changeState('orders');
                })
            })
        }
    }
})

Vue.component('order', {
    data: function () {
        return {
            orderId: this.order.id,
        }
    },
    props: ['order', 'changeFn'],
    template: `<tr>
                    <td>{{order.address}}</td>
                    <td>{{order.productionMethod}}</td>
                    <td>{{order.paymentMethod}}</td>
                    <td>{{order.orderDate}}</td>
                    <td><input type="button" value="Details" @click="changeFn(order)"/></td>
               </tr>`,
})

Vue.component('orders', {
    data: function () {
        return {
            orders: [],
            selectedOrder: null,
        }
    },
    template: `<table v-if="selectedOrder === null" border="1">
                    <th><strong>Address</strong></th>
                    <th><strong>Receipt method</strong></th>
                    <th><strong>Payment method</strong></th>
                    <th><strong>Date</strong></th>
                    <th><strong>Details</strong></th>
                    <order v-for="order in orders" :key="order.id" :order="order" :changeFn="changeComponent"></order>
               </table>
               <order-about v-else :order="selectedOrder" :changeFn="changeComponent"/>`,
    created: function () {
        ordersApi.get({id: this.$root.userId}, '').then(response => {
            response.json().then(data => {
                data.forEach(order => this.orders.push(order))
            })
        }, reason => alert('Orders is empty'))
    },
    methods: {
        changeComponent: function (order) {
            this.selectedOrder = order;
        }
    }
})

Vue.component('registration', {
    data: function () {
        return {
            email: '',
            fio: ''
        }
    },
    template: `<div>
                    <input type="text" v-model="email" placeholder="Email"/>
                    <input type="text" v-model="fio" placeholder="Name"/>
                    <input type="button" value="Registration" @click="submit" />
               </div>`,
    methods: {
        submit: function () {
            let user = {email: this.email, fio: this.fio}
            console.log(user);
            createUserApi.save({}, user).then(response => {
                response.json().then(data => {
                    createCartApi.save({id: data.id}, 'create');
                    this.$root.changeState('login')
                })

            }, reason => {
                alert('The user with this mail already exists')
            })
        }
    }
})

Vue.component('navigation-buttons', {
    props: ['buttons', 'changeFn'],
    template: `<div>
                    <button v-for="btn in buttons" v-bind:key="btn" @click="changeFn(btn)">{{btn}}</button>                  
               </div>`
})


var app = new Vue({
    el: '#app',
    template: `<div>
                    <navigation-buttons :buttons="buttons[navigationState]" :changeFn="changeState"/>
                    <title-block :title="title[pageState]"/>
                    <component v-bind:is="pageState" />
               </div>`,
    data: {
        title: {
            login: 'Login',
            products: 'Products',
            registration: 'Registration',
            cart: 'Cart',
            orders: 'Orders',
        },
        navigationState: 'login',
        pageState: 'login',
        buttons: {
            login: ['Login', 'Registration'],
            main: ['Products', 'Cart', 'Orders', 'Logout']
        }

    },
    methods: {
        login: function (id) {
            this.userId = id;
            this.navigationState = 'main';
            this.pageState = 'products';
        },
        changeState: function (state) {
            if (state == 'Logout') {
                this.pageState = 'login';
                this.navigationState = 'login';
            } else this.pageState = state.toLowerCase();
        }
    }
})