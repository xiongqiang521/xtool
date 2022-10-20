package com.xq.xtool.excel.csv;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Description:
 *
 * @author 13797
 * @version v0.0.1
 * 2021/11/11 23:02
 */
public class CSVDemo {
    public static void main(String[] args) {
        List<UserDTO> list = getList();

        String handle = CSVHandler.handle(UserCSVFieldEnum.class, list);

        System.out.println(handle);
    }

    private static List<UserDTO> getList() {
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(new UserDTO("1", null, "123"));
        userDTOS.add(new UserDTO("2", "旮,旯", "123"));
        userDTOS.add(new UserDTO("3", "圭\"臬", "123"));
        return userDTOS;
    }

    public static enum UserCSVFieldEnum implements CSVField<UserDTO> {
        user_id("用户id", UserDTO::getUserId),
        user_name("用户名", UserDTO::getUserName),
        password("密码", UserDTO::getPassword),
        ;

        private final String name;
        private final Function<UserDTO, String> fun;

        UserCSVFieldEnum(String name, Function<UserDTO, String> fun) {
            this.name = name;
            this.fun = fun;
        }

        @Override
        public String getDict() {
            return this.name();
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        @NotNull
        public Function<UserDTO, String> getValue() {
            return this.fun;
        }
    }

    public static class UserDTO {
        private String userId;
        private String userName;
        private String password;

        public UserDTO(String userId, String userName, String password) {
            this.userId = userId;
            this.userName = userName;
            this.password = password;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
