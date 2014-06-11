--测试用户
INSERT INTO t_ec_EcUser (id, name) VALUES ('1', 'tom');
INSERT INTO T_ec_EcUser (id, name) VALUES ('2', 'sam1');
INSERT INTO T_ec_EcUser (id, name) VALUES ('3', 'sam2');

--测试面料分类
INSERT INTO T_ec_fabricCategory (id, name,isValid) VALUES ('1', 'category1',0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id,isValid) VALUES ('2', 'category1-1', 1,0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id,isValid) VALUES ('3', 'category1-2', 1,0);
INSERT INTO T_ec_fabricCategory (id, name,isValid) VALUES ('4', 'category2',0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id,isValid) VALUES ('5', 'category2-1', 4,0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id,isValid) VALUES ('6', 'category2-2', 4,0);

--辅料分类测试数据
INSERT INTO T_ec_MaterialCategory (id, name) VALUES ('1', 'category1');
INSERT INTO T_ec_MaterialCategory (id, name, parent_id) VALUES ('2', 'category1-1', '1');
INSERT INTO T_ec_MaterialCategory (id, name, parent_id) VALUES ('3', 'category1-2', '1');
INSERT INTO T_ec_MaterialCategory (id, name) VALUES ('4', 'category2');
INSERT INTO T_ec_MaterialCategory (id, name, parent_id) VALUES ('5', 'category2-1', '4');
INSERT INTO T_ec_MaterialCategory (id, name, parent_id) VALUES ('6', 'category2-2', '4');

--测试item
INSERT INTO T_ec_Item (id, customId, name, createdBy, createdTime,state)
VALUES ('1', '123', '测试面料', '1', '2013-1-1 14:12:15','草稿');
INSERT INTO T_ec_Item (id, customId, name, createdBy, createdTime,state)
VALUES ('2', '234', '测试辅料1', '1', '2013-1-1 14:13:15','出售中');
INSERT INTO T_ec_Item (id, customId, name, createdBy, createdTime,state)
VALUES ('3', '345', '测试辅料2', '1', '2013-1-1 14:14:15','下架');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime,state)
VALUES ('4', 'item4', '789', '2', '2013-1-1 14:15:15','草稿');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime,state)
VALUES ('5', 'item5', '901', '2', '2013-1-1 14:16:15','出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime,state)
VALUES ('6', 'item6', '134', '2', '2013-1-1 14:17:15','下架');

--测试面料成分
INSERT INTO T_ec_FabricSource (id, name) VALUES ('1', 'sourceparent1');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('3', 'sourcechild1-1', '1');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('4', 'sourcechild1-2', '1');
INSERT INTO T_ec_FabricSource (id, name) VALUES ('2', 'source1');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('5', 'sourcechill2-1', '2');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('6', 'sourcechild2-2', '2');

--测试面料
INSERT INTO T_ec_Fabric (id, category_id, source_id, detailSource_id) VALUES
  ('1', '2', '1', '3');
INSERT INTO T_ec_Fabric (id, category_id) VALUES ('2', '2');
INSERT INTO T_ec_Fabric (id, category_id) VALUES ('3', '2');
INSERT INTO T_ec_Fabric (id, category_id) VALUES ('4', '3');
INSERT INTO T_ec_Fabric (id, category_id) VALUES ('5', '4');

--测试辅料
INSERT INTO T_ec_Material (id, category_id) VALUES ('2', '2');
INSERT INTO T_ec_Material (id, category_id) VALUES ('3', '2');
INSERT INTO T_ec_Material (id, category_id) VALUES ('6', '2');

--测试面料价格
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('1', 5, 20);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('1', 10, 15);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('1', 20, 10);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('2', 30, 40);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('2', 40, 35);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('3', 30, 40);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('3', 50, 70);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('4', 40, 35);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('5', 30, 40);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('5', 40, 35);


--评价测试数据
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('1', '1', '1', '2014-5-19 12:23:34', '好评', '11', 3, 4, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('2', '1', '1', '2014-5-19 12:23:34', '中评', '22', 4, 5, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('3', '1', '1', '2014-5-19 12:23:34', '差评', '33', 5, 5, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('4', '1', '1', '2014-5-19 12:23:34', '好评', '44', 4, 3, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('5', '1', '1', '2014-5-19 12:23:34', '中评', '55', 2, 2, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('6', '1', '1', '2014-5-19 12:23:34', '差评', '66', 3, 5, 5);

INSERT INTO T_ec_orderItem (id, orderNo, buyer_id, seller_id, createdTime)
VALUES ('o1', 'eeee1', '1', '2', '2014-5-22 12:23:33');
INSERT INTO T_ec_orderItem (id, orderNo, buyer_id, seller_id, createdTime)
VALUES ('o2', 'eeee2', '1', '2', '2014-5-22 12:23:33');
INSERT INTO T_ec_orderItem (id, orderNo, buyer_id, seller_id, createdTime)
VALUES ('o3', 'eeee3', '1', '2', '2014-5-22 12:23:33');
INSERT INTO T_ec_orderItem (id, orderNo, buyer_id, seller_id, createdTime)
VALUES ('o4', 'eeee4', '1', '2', '2014-5-22 12:23:33');
INSERT INTO T_ec_orderItem (id, orderNo, buyer_id, seller_id, createdTime)
VALUES ('o5', 'eeee5', '1', '2', '2014-5-22 12:23:33');
INSERT INTO T_ec_orderItem (id, orderNo, buyer_id, seller_id, createdTime)
VALUES ('o6', 'eeee6', '1', '2', '2014-5-22 12:23:33');

INSERT INTO T_ec_OrderLine (id, sum, quantity, orderItem_id, item_id, price)
VALUES ('011', '111', '1', 'o1', '1', 3.23);
INSERT INTO T_ec_OrderLine (id, sum, quantity, orderItem_id, item_id, price)
VALUES ('012', '112', '2', 'o1', '1', 232.23);
INSERT INTO T_ec_OrderLine (id, sum, quantity, orderItem_id, item_id, price)
VALUES ('022', '111', '1', 'o2', '1', 232.32);

--测试收货地址
INSERT INTO T_ec_address (id, user_id, state, city, zipCode, street, receiverName, mobile, zipPhone, phone, childPhone, createdTime, updatedTime)
VALUES ('1', '1', '上海', '上海', '203323', '浦东新区浦电路370号', 'Charles', '1862229323', '021', '23839434', '2343', '2014-05-28',
        '2014-05-28');
INSERT INTO T_ec_address (id, user_id, state, city, zipCode, street, receiverName, mobile, zipPhone, phone, childPhone, createdTime, updatedTime)
VALUES ('2', '1', '上海', '上海', '203323', '浦东新区浦电路370号', 'Sam', '1862229342', '021', '23839434', '2222', '2014-04-28',
        '2014-04-28');
UPDATE T_ec_EcUser
SET defaultAddress_id = '1'
WHERE id = '1';
--求购单测试
INSERT INTO t_ec_demandorder (id, address_id, demandType, user_id, title,createdTime,validDateTo) VALUES ('1', '1', '服务', '1', 'demandOrder','2014-6-12 12:00:12','2014-6-30 12:00:12');

--测试调样单
INSERT INTO T_ec_SampleOrder (id, user_id, item_id, state, address_id) VALUES ('1', '1', '1', '已发货', '1');
INSERT INTO T_ec_SampleOrder (id, user_id, item_id, state, address_id) VALUES ('2', '1', '2', '已发货', '1');

--关注的商品
INSERT INTO t_ec_favouriteItems (user_id, item_id, createdTime) VALUES ('1', '2', '2014-4-4 12:12:12');
INSERT INTO t_ec_favouriteItems (user_id, item_id, createdTime) VALUES ('1', '3', '2014-4-5 12:12:12');
INSERT INTO t_ec_favouriteItems (user_id, item_id, createdTime) VALUES ('1', '4', '2014-4-6 12:12:12');

--测试店铺
INSERT INTO t_ec_shop (id, user_id) VALUES ('1', '2');

--测试收藏的店铺
INSERT INTO t_ec_favouriteShops (shop_id, user_id, createdTime) VALUES ('1', '1', '2014-4-6 12:12:12');

--测试广告栏位
INSERT INTO T_ec_ad_position (id, positionNo, name, description, isValid)
VALUES ('wewew', 'ewewewewe', 'wewwewe', 'dsadasdsadsda', 0);
--测试广告栏位
INSERT INTO T_ec_ad (id, position_id, title, link, coverPath, isValid)
VALUES ('asdsadasd', 'wewew', 'fsdfsdfsdfsd', 'asdsadsadsadsa', 'xcvxcvxcvxvcxv', 0);

--卖家评价数据
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('1', '1', '1', '好评', '11', '2014-6-4 12:23:00');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('2', '1', '1', '好评', '22', '2014-6-4 12:24:00');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('3', '1', '1', '好评', '33', '2014-6-4 12:25:00');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('4', '1', '1', '好评', '44', '2014-6-4 12:26:00');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('5', '1', '1', '好评', '55', '2014-6-4 12:27:00');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('6', '1', '1', '中评', '66', '2014-6-4 12:28:00');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('7', '1', '1', '中评', '77', '2014-6-4 12:29:00');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('8', '1', '1', '中评', '88', '2014-6-4 12:23:01');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('9', '1', '1', '中评', '99', '2014-6-4 12:23:02');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('10', '1', '1', '好评', '99', '2014-6-4 12:23:03');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('11', '1', '1', '好评', '99', '2014-6-4 12:23:04');
