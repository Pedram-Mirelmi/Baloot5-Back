package ie.baloot5.data;


public class StaticData {
    public static String commoditiesString = """
            [{"id":1,"name":"Onion","providerId":1,"price":10000,"categories":["Vegetables"],"rating":7.0,"inStock":50},
            {"id":2,"name":"Potato","providerId":1,"price":14000,"categories":["Vegetables"],"rating":7.0,"inStock":60},
            {"id":3,"name":"Carrot","providerId":1,"price":25000,"categories":["Vegetables"],"rating":7.0,"inStock":50},
            {"id":4,"name":"Celery","providerId":1,"price":15000,"categories":["Vegetables"],"rating":6.5,"inStock":20},
            {"id":5,"name":"parsley","providerId":1,"price":15000,"categories":["Vegetables"],"rating":7.0,"inStock":20},
            {"id":6,"name":"Broccoli","providerId":1,"price":40000,"categories":["Vegetables"],"rating":8.0,"inStock":25},
            {"id":7,"name":"Cabbage","providerId":1,"price":30000,"categories":["Vegetables"],"rating":6.0,"inStock":30},
            {"id":8,"name":"Lettuce","providerId":1,"price":25000,"categories":["Vegetables"],"rating":7.0,"inStock":50},
            {"id":9,"name":"Galaxy A73","providerId":2,"price":17000000,"categories":["Phone","Technology"],"rating":8.0,
            "inStock":10},{"id":10,"name":"Galaxy S23 Plus","providerId":2,"price":57600000,"categories":["Phone","Technology"],"rating":8.3,"inStock":5},
            {"id":11,"name":"Galaxy S23 Ultra","providerId":2,"price":65000000,"categories":["Phone","Technology"],"rating":8.7,"inStock":7},
            {"id":12,"name":"Galaxy A53","providerId":2,"price":15000000,"categories":["Phone","Technology"],"rating":7.6,"inStock":20},
            {"id":13,"name":"Galaxy Z Fold 4","providerId":2,"price":75300000,"categories":["Phone","Technology"],"rating":7.9,"inStock":5},
            {"id":14,"name":"Galaxy Z Flip 4","providerId":2,"price":43500000,"categories":["Phone","Technology"],"rating":7.7,"inStock":8},
            {"id":15,"name":"Iphone 14 Pro Max","providerId":2,"price":73500000,"categories":["Phone","Technology"],"rating":9.2,"inStock":20},
            {"id":16,"name":"Iphone 14 Pro","providerId":2,"price":65000000,"categories":["Phone","Technology"],"rating":9.0,"inStock":10},
            {"id":17,"name":"Iphone 14 Plus","providerId":2,"price":60250000,"categories":["Phone","Technology"],"rating":8.8,"inStock":14},
            {"id":18,"name":"Iphone 14","providerId":2,"price":59300000,"categories":["Phone","Technology"],"rating":9.0,"inStock":3},
            {"id":19,"name":"Xiaomi 12 Pro","providerId":2,"price":31000000,"categories":["Phone","Technology"],"rating":9.5,"inStock":17},
            {"id":20,"name":"Xiaomi Mi 11","providerId":2,"price":23000000,"categories":["Phone","Technology"],"rating":8.3,"inStock":23},
            {"id":21,"name":"Type C Super Fast Charger Adaptor","providerId":3,"price":700000,"categories":["Phone","Technology","Phone Accessory"],"rating":8.0,"inStock":10},
            {"id":22,"name":"Type C Cable","providerId":3,"price":120000,"categories":["Phone","Technology","Phone Accessory"],"rating":8.3,"inStock":5},
            {"id":23,"name":"Mini USB Cable","providerId":3,"price":70000,"categories":["Phone","Technology","Phone Accessory"],"rating":8.7,"inStock":7},
            {"id":24,"name":"Galaxy Buds 2 Pro","providerId":3,"price":6120000,"categories":["Phone","Technology","Phone Accessory"],"rating":7.6,"inStock":20},
            {"id":25,"name":"Galaxy Buds Pro","providerId":3,"price":5700000,"categories":["Phone","Technology","Phone Accessory"],"rating":7.9,"inStock":5},
            {"id":26,"name":"Galaxy Buds 2","providerId":3,"price":3900000,"categories":["Phone","Technology","Phone Accessory"],"rating":7.7,"inStock":8},
            {"id":27,"name":"Airpod Pro","providerId":3,"price":11000000,"categories":["Phone","Technology","Phone Accessory"],"rating":9.2,"inStock":20},
            {"id":28,"name":"Lightning Cable","providerId":3,"price":200000,"categories":["Phone","Technology","Phone Accessory"],"rating":8.3,"inStock":23},
            {"id":29,"name":"Apple","providerId":4,"price":13000,"categories":["Fruits"],"rating":7.0,"inStock":50},
            {"id":30,"name":"Mango","providerId":4,"price":240000,"categories":["Fruits"],"rating":7.0,"inStock":60},
            {"id":31,"name":"Orange","providerId":4,"price":25000,"categories":["Fruits"],"rating":7.0,"inStock":50},
            {"id":32,"name":"Banana","providerId":4,"price":63000,"categories":["Fruits"],"rating":6.5,"inStock":20},
            {"id":33,"name":"Tangerine","providerId":4,"price":35000,"categories":["Fruits"],"rating":7.0,"inStock":20},
            {"id":34,"name":"Grapefruit","providerId":4,"price":400000,"categories":["Fruits"],"rating":8.0,"inStock":25},
            {"id":35,"name":"Kiwi","providerId":4,"price":30000,"categories":["Fruits"],"rating":6.0,"inStock":30},
            {"id":36,"name":"Pineapple","providerId":4,"price":125000,"categories":["Fruits"],"rating":7.0,"inStock":50},
            {"id":37,"name":"Pear","providerId":4,"price":80000,"categories":["Fruits"],"rating":7.0,"inStock":50},
            {"id":38,"name":"Passionfruit","providerId":4,"price":240000,"categories":["Fruits"],"rating":7.0,"inStock":60},
            {"id":39,"name":"Plum","providerId":4,"price":50000,"categories":["Fruits"],"rating":7.0,"inStock":50},
            {"id":40,"name":"Melon","providerId":4,"price":30000,"categories":["Fruits"],"rating":6.5,"inStock":20},
            {"id":41,"name":"Coconut","providerId":4,"price":150000,"categories":["Fruits"],"rating":7.0,"inStock":20},
            {"id":42,"name":"Cherry","providerId":4,"price":60000,"categories":["Fruits"],"rating":8.0,"inStock":25},
            {"id":43,"name":"Blackberry","providerId":4,"price":300000,"categories":["Fruits"],"rating":6.0,"inStock":30}]""";

    public static String usersString = """
            [{"username":"amir","password":"1234","email":"amir@gmail.com","birthDate":"2000-01-01","address":"amir-home","credit":25000},
            {"username":"hamid","password":"12345","email":"hamid@gmail.com","birthDate":"2002-02-25","address":"hamid-home","credit":3900000},
            {"username":"ali","password":"4321","email":"ali@gmail.com","birthDate":"1942-10-11","address":"ali-home","credit":4300000},
            {"username":"parsa","password":"54321","email":"parsa@gmail.com","birthDate":"1987-07-03","address":"parsa-home","credit":12000000},
            {"username":"soroosh","password":"3421","email":"soroosh@gmail.com","birthDate":"1990-05-15","address":"soroosh-home","credit":0},
            {"username":"mahdi","password":"4312","email":"mahdi@gmail.com","birthDate":"1999-04-27","address":"mahdi-home","credit":20000000}]""";

    public static String providersString = """
            [{"id":1,"name":"Vegetable Provider","registryDate":"2023-01-01"},
            {"id":2,"name":"Phone Provider","registryDate":"2022-11-10"},
            {"id":3,"name":"Phone Accessory Provider","registryDate":"2020-02-25"},
            {"id":4,"name":"Fruit Provider","registryDate":"2022-12-15"},
            {"id":5,"name":"Home and Kitchen Appliances Provider","registryDate":"2021-10-01"},
            {"id":6,"name":"Dairy Provider","registryDate":"2022-11-11"}]""";

    public static String commentsString = """
            [{"userEmail":"amir@gmail.com","commodityId":25,"text":"fair price for a good quality","date":"2021-03-10"},
            {"userEmail":"hamid@gmail.com","commodityId":6,"text":"don't like how it tastes","date":"2023-02-03"},
            {"userEmail":"amir@gmail.com","commodityId":11,"text":"One of the bests in the current market","date":"2021-03-10"},
            {"userEmail":"ali@gmail.com","commodityId":28,"text":"the case was torn apart, and the cable did not charge. Would not recommend!","date":"2021-06-03"},
            {"userEmail":"parsa@gmail.com","commodityId":19,"text":"fair price for a good quality","date":"2022-12-10"},
            {"userEmail":"soroosh@gmail.com","commodityId":32,"text":"there were only 4 in the package. I expected to be more!!","date":"2022-09-12"},
            {"userEmail":"mahdi@gmail.com","commodityId":34,"text":"Not worth it.","date":"2023-03-10"}]""";


    public static String discounts = """
            [{"discountCode":"HAPPY_NOWRUZ","discount":25},
            {"discountCode":"IE1402","discount":15},
            {"discountCode":"EVERYTHING_IS_ON_SALE","discount":60},
            {"discountCode":"DISCOUNT_FOR_NEW_YEAR","discount":20},
            {"discountCode":"40_PERCENT_OFF","discount":40}]""";
}