package ru.sfu.zooshop.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.utility.ResponseWriter;

import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@Component
public class ApiSignOutSuccessHandler {
  private final ResponseWriter responseWriter;

  public void onSignOutSuccess(HttpServletRequest request, HttpServletResponse response) {
    Response signOutResponse = getResponse(
      request,
      OK,
      "You have signed out",
      null
    );
    responseWriter.write(response, OK, signOutResponse);
  }
}
