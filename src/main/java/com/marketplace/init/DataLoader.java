package com.marketplace.init;

import com.marketplace.entity.Item;
import com.marketplace.entity.Role;
import com.marketplace.repository.ItemRepository;
import com.marketplace.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static com.marketplace.entity.Role.Name.ROLE_ADMIN;
import static com.marketplace.entity.Role.Name.ROLE_USER;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final ItemRepository itemRepository;
   // private final UserItemRepository userItemRepository;

    public void run(ApplicationArguments args) {
        roleRepository.save(new Role(ROLE_ADMIN));
        roleRepository.save(new Role(ROLE_USER));

        itemRepository.save(new Item(1L, "Lenovo 15ARH05 82EY00F6RE", "15.6\" 1920 x 1080 IPS, 120 Гц, несенсорный, AMD Ryzen 5 4600H 3000 МГц, 16 ГБ, SSD 512 ГБ, видеокарта NVIDIA GeForce GTX 1650 черный", "lvo,ryzen,16,nvidia", 1l));
        itemRepository.save(new Item(2L, "Lenovo IdeaPad Gaming 3 15ARH05 82EY00F6RE", "15.6\" 1920 x 1080 IPS, 120 Гц, несенсорный, AMD Ryzen 5 4600H 3000 МГц, 16 ГБ, SSD 512 ГБ, видеокарта NVIDIA GeForce GTX 1650 Ti 4 ГБ, Windows 10, цвет крышки черный", "lenovo,ryzen,16,nvidia", 2l));
        itemRepository.save(new Item(3L, "HP Pavilion 15-eg0045ur 2P1P2EA", "15.6\" 1920 x 1080 IPS, 60 Гц, несенсорный, Intel Core i3 1115G4 3000 МГц, 8 ГБ, SSD 256 ГБ, видеокарта встроенная, без ОС, цвет крышки серебристый", "hp,2p1p2ea,15,6,60,intel", 2l));
        itemRepository.save(new Item(4L, "a", "a", "a", 10l));

//        for(long i =1L;i<=4L;i++){
//            UserItem userItem = new UserItem();
//            //userItem.setItem(itemRepository.findItemById(i));
//            userItem.setItemId(i);
//            userItem.setQuantity(0L);
//            userItemRepository.save(userItem);
//        }

    }
}
