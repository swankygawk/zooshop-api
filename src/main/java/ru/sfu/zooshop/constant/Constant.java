package ru.sfu.zooshop.constant;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Constant {
  public static final Long SYSTEM_ID = 0L;
  public static final Long SYSTEM_ROLE_ID = 2L;
  public static final Long ADMIN_ID = 5L;
  public static final Long ADMIN_ROLE_ID = 3L;
  public static final Long USER_ROLE_ID = 6L;
//  public static final String SYSTEM_ROLE = "SYSTEM";
//  public static final String ADMIN_ROLE = "ADMIN";
  public static final Path FILE_STORAGE_LOCATION = Paths.get(System.getProperty("user.dir") + "/upload").toAbsolutePath().normalize();
  public static final Path PROFILE_PICTURE_STORAGE_LOCATION = Paths.get(FILE_STORAGE_LOCATION.toString(), "/profile").toAbsolutePath().normalize();
  public static final Path CATEGORY_PICTURE_STORAGE_LOCATION = Paths.get(FILE_STORAGE_LOCATION.toString(), "/category").toAbsolutePath().normalize();
  public static final Path SUBCATEGORY_PICTURE_STORAGE_LOCATION = Paths.get(FILE_STORAGE_LOCATION.toString(), "/subcategory").toAbsolutePath().normalize();
  public static final Path PRODUCT_PICTURE_STORAGE_LOCATION = Paths.get(FILE_STORAGE_LOCATION.toString(), "/product").toAbsolutePath().normalize();
  public static final String DEFAULT_PROFILE_PICTURE_URL = "http://localhost:8080/api/v1/picture/profile/default.png";
  public static final String DEFAULT_CATEGORY_PICTURE_URL = "http://localhost:8080/api/v1/picture/category/default.png";
  public static final String DEFAULT_SUBCATEGORY_PICTURE_URL = "http://localhost:8080/api/v1/picture/subcategory/default.png";
  public static final String DEFAULT_PRODUCT_PICTURE_URL = "http://localhost:8080/api/v1/picture/product/default.png";
  public static final String BASE_PATH = "/**";
  public static final String CONTEXT_PATH = "/api/v1";
  public static final String ZOOSHOP = "ZooShop";
  public static final Set<String> ZOOSHOP_USERS = Set.of("ZooShop Users");
//  public static final String EMPTY_VALUE = "empty";
//  public static final String ROLE_PREFIX = "ROLE_";
  public static final String ROLE = "role";
//  public static final String AUTHORITY_DELIMITER = ",";
//  public static final String AUTHORITIES = "authorities";
  public static final Long JWT_ACCESS_EXPIRATION = 1_800_000L; // 30 minutes in milliseconds
  public static final Integer JWT_ACCESS_EXPIRATION_SECONDS = (int) (JWT_ACCESS_EXPIRATION / 1000); // 30 minutes in seconds
  public static final Long JWT_REFRESH_EXPIRATION = 3_600_000L; // 1 hour in milliseconds
  public static final Integer JWT_REFRESH_EXPIRATION_SECONDS = (int) (JWT_REFRESH_EXPIRATION / 1000); // 1 hour in seconds
  public static final String JWT_SECRET = "139B9841DCEC9D8ABBD45EC6F7E0EEABD384674BB95344A9C9EBBC04C0049A8BC80ADAB770D88102BD23A60B7E87977BB18599C9EFCA478A425ECEC6F10179B0";
  public static final Short CREDENTIAL_EXPIRATION_DAYS = 90;
  public static final String SIGN_IN_PATH = "/auth/signIn";
  public static final String SIGN_OUT_PATH = "/auth/signOut";
  public static final List<String> PUBLIC_ROUTES = Stream.of("/auth/signUp", "/auth/verify", "/auth/verify/resend", "/auth/signIn", "/auth/reset").map(route -> CONTEXT_PATH + route).toList();
}
