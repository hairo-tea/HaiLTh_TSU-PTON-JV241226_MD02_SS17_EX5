create database session17_ex5;
use session17_ex5;

create table orders
(
    id           int primary key auto_increment,
    customer_id  int     not null,
    total_amount decimal not null,
    product_id   int     not null,
    quantity     int     not null
);
create table products
(
    id           int primary key auto_increment,
    product_name varchar(100) not null,
    stock        int
);
insert into products (product_name, stock)
VALUES ('Laptop Dell Pro', 50),
       ('Iphone 20', 100);

#procedure
delimiter //
create procedure place_order(
    IN in_customer_id int,
    IN in_total_amount decimal,
    IN in_product_id int,
    IN in_quantity int,
    out result varchar(255)
)
begin
    declare available_stock int;

    -- kiểm tra hàng tồn kho
SELECT stock into available_stock from products where id = in_product_id;
if available_stock is null then
        set result = 'Sản phẩm không tồn tại';
        -- Thêm thông báo debug
        elseif available_stock < in_quantity then
            set result = 'Không đủ hàng trong kho';
else
            -- Thực hiện đặt hàng
            insert into orders(customer_id, total_amount, product_id, quantity)
            VALUES (in_customer_id, in_total_amount, in_product_id, in_quantity);

            -- cập nhật hàng tồn kho
UPDATE products
set stock = stock - in_quantity
where id = in_product_id;
SET result = 'Đặt hàng thành công';
end if;
end//
delimiter ;



