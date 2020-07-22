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

Vue.component('order-form',{
    data: function () {
        return {
            paymentMethod: '',
            productionMethod: '',
            address: '',
        }
    },
    template: `<div>
                    <span>Заказать</span>
                    <input type="text" v-model="address" placeholder="Адрес">
                    <input type="text" v-model="productionMethod" placeholder="Способ получения">
                    <input type="text" v-model="paymentMethod" placeholder="Способ оплаты">
                    <input type="button" value="Подтвердить" @click="submit">
               </div>`,
    methods: {
        submit: function () {
            let order = {
                address: this.address,
                productionMethod: this.productionMethod,
                paymentMethod: this.paymentMethod
            };
            orderSubmitApi.save({id: this.$root.userId},order).then(response => {
                alert('Заказ успешно оформлен!');
                createCartApi.save({id: this.$root.userId},'create').then(response => {
                    this.$root.changeState('orders');
                })
            })
        }
    }
})

Vue.component('item', {
    data: function () {
        return {
            vendorCode: this.item.product.vendorCode,
            count: this.item.count,
            fullPrice: this.item.product.price * this.item.count,
            price: this.item.product.price
        }
    },
    props: {
        item: null,
        items: null,
    },
    template: '<tr>' +
        '<td><strong>{{item.product.name}}</strong></td>' +
        '<td><strong>{{this.fullPrice}}</strong></td>' +
        '<td><strong>{{this.count}}</strong></td>' +
        '<input type="button" value="-" v-on:click="dec">' +
        '<input type="button" value="+" v-on:click="inc">' +
        '</tr>',
    methods: {
        inc: function () {
            incApi.save({id: this.$root.userId, vendorCode: this.vendorCode}, 'add').then(response => {
                this.count++;
                this.fullPrice += this.price;
            }, reason => {
                console.log(reason);
            })
        },
        dec: function () {
            decApi.save({id: this.$root.userId, vendorCode: this.vendorCode}, 'delete').then(response => {
                if (this.count > 1) {
                    this.count--;
                    this.fullPrice -= this.price;
                } else {
                    this.items.splice(this.items.indexOf(this.item), 1);
                }
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
        '<table>' +
        '<item v-for="item in items" v-bind:key="item.id" v-bind:item="item" v-bind:items="items"/>' +
        '</table>' +
        '<order-form v-if="items.length"/>'+
        '</div>',
    created: function () {
        itemsApi.get({id: this.$root.userId}).then(result =>
            result.json().then(data => {
                    data.shopCartItems.forEach(item => this.items.push(item))
                    // console.log(data);
                }
            )
        )
    }
})


Vue.component('product-row', {
    data: function () {
        return {
            vendorCode: this.product.vendorCode,
        }
    },
    props: ['product'],
    template: '<tr> <td><strong>{{product.name}}</strong></td>  <td>{{product.description}}</td>' +
        '<td><input type="button" value="В корзину" v-on:click="add"></td></tr>',
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
    template: '<table>' +
        '<product-row v-for="product in products" v-bind:key="product.id" v-bind:product="product"  />' +
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
                    <input type="button" value="Вход" @click="submit"/>
               </div>`,
    methods: {
        submit: function () {
            userApi.get({email: this.email}, '').then(response => {
                response.json().then(data => this.$root.login(data.id))
            }, reason => console.log('NotFound'))
        }
    }
})

Vue.component('order', {
    data: function () {
        return {
            orderId: this.order.id,
        }
    },
    props: ['order'],
    template: `<tr>
                    <td>{{order.address}}</td>
                    <td>{{order.productionMethod}}</td>
                    <td>{{order.paymentMethod}}</td>
                    <td>{{order.date}}</td>
               </tr>`,
})

Vue.component('orders',{
    data: function () {
        return {
            orders: []
        }
    },
    template: `<table>
                    <order v-for="order in orders" :key="order.id" :order="order"></order>
               </table>`,
    created: function () {
        ordersApi.get({id:this.$root.userId},'').then(response => {
            response.json().then(data => {
                data.forEach(order => this.orders.push(order))
            })
        },reason => alert('Нет заказов'))
    }
})

Vue.component('registration',{
    data: function () {
        return {
            email : '',
            fio : ''
        }
    },
    template: `<div>
                    <input type="text" v-model="email" placeholder="Email"/>
                    <input type="text" v-model="fio" placeholder="Фио"/>
                    <input type="button" value="Подтвердить" @click="submit" />
               </div>`,
    methods: {
        submit: function () {
            let user = {email: this.email, fio: this.fio}
            console.log(user);
            createUserApi.save({},user).then(response => {
                response.json().then(data => {
                    createCartApi.save({id: data.id},'create');
                    this.$root.changeState('login')
                })

            },reason => {
                alert('Пользователь с данной почтой уже существует')
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
            login: 'Вход',
            products: 'Продукты',
            registration: 'Введите данные',
            cart: 'Ваша корзина',
            orders: 'Ваши заказы',
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