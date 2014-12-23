INSERT INTO task (id, is_change, is_rent, is_sale, house_number, number, url, implementation_id, area_city_id, city_id, country_id, district_id, district_city_id, region_id, street_id, owner_id) VALUES
(1, b'0', b'0', b'1', NULL, NULL, 'http://kiev.ko.olx.ua/nedvizhimost/prodazha-kvartir/', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2),
(2, b'0', b'0', b'1', NULL, NULL, 'http://kiev.ko.olx.ua/nedvizhimost/prodazha-domov/', 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2);
INSERT INTO task_history (id, count_duplicated, count_found, count_new, count_update, end_date, start_date, state_id, task_id) VALUES
(1, 2, 5, 1, 2, '2014-11-04 12:00:00', '2014-11-04 00:00:00', NULL, 1),
(2, 3, 10, 3, 4, '2014-11-12 12:00:00', '2014-11-12 11:30:00', NULL, 2);