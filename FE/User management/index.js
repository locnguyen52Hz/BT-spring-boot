$(document).ready(function () {
  let token = localStorage.getItem("token");
  console.log(token);
  //lấy danh sách người dùng
  $.ajax({
    type: "GET",
    url: "http://localhost:8080/users",
    headers: {
      Authorization: `Bearer ${token}`,
    },
    success: function (userList) {
      console.log(userList);
      userList.forEach((user) => {
        const row = `
        <tr >
          <td>${getValiField(user, "email")}</td>
          <td>${getValiField(user, "fullName")}</td>
          <td><image width="50" src = "http://localhost:8080/users/avatar/${
            getValiField(user, "avatar") || "default-avatar.png"
          }" /></td>
          
          <td>${getValiField(user, "website")}</td>
          <td>${getValiField(user, "phone")}</td>
          <td>${getValiField(user, "facebook")}</td>
          <td>${getValiField(user, "roleId")}</td>
          <td>${getValiField(user, "address")}</td>
          <td><button class= "btnModal" user-email = "${getValiField(
            user,
            "email"
          )}" 
            user-fullName = "${getValiField(user, "fullName")}" 
            user-avatar="${getValiField(user, "avatar")}" 
            user-website = "${getValiField(user, "website")}" 
            user-phone = "${getValiField(user, "phone")}" 
            user-facebook = "${getValiField(user, "facebook")}"
            user-roleId = "${user.roleId}" 
            user-address = "${getValiField(user, "address")}" 
            user-id = "${user.id}">Sửa</button>
          </td>
          <td><button class = "deleteBtn" id = '${user.id}'>Xóa</button></td>
        </tr>
      `;
        $("#userListTb").append(row);
      });
    },
    error: function (xhr, status, error) {
      console.error("Lỗi:", xhr.status, xhr.responseText);
    },
  });

  let inputFieldModal;
  let userDetail;
  let userDetailUpdate;

  function getValiField(obj, field) {
    const value = obj?.[field];
    return value && value !== "null" ? value : "";
  }

  //show modal
  $(document).on("click", ".btnModal", function () {
    $("#userModal").css("display", "block");

    userDetail = {
      id: $(this).attr("user-id"),
      email: $(this).attr("user-email"),
      fullName: $(this).attr("user-fullName"),
      avatar: $(this).attr("user-avatar"),
      website: $(this).attr("user-website"),
      phone: $(this).attr("user-phone"),
      facebook: $(this).attr("user-facebook"),
      roleId: $(this).attr("user-roleId"),
      address: $(this).attr("user-address"),
    };

    inputFieldModal = `
      <div>
          <label for="email">Email</label>
          <input class= "input-field" type="email" id="email" value="${getValiField(
            userDetail,
            "email"
          )}">

      </div>
      <div>
          <label for="">Full Name</label>
          <input class= "input-field" type="text" id="fullName"value="${getValiField(
            userDetail,
            "fullName"
          )}">
      </div>
      <div>
          <label for="avatar">Avatar</label>
          <input type="file" id="avatar">
      </div>
      <div>
          <label for="">Website</label>
          <input class= "input-field" type="text" id="website" value = "${getValiField(
            userDetail,
            "website"
          )}">
      </div>
      <div>
          <label for="">Phone</label>
          <input class= "input-field" type="tel" id="phone" value = "${getValiField(
            userDetail,
            "phone"
          )}">
      </div>
      <div>
          <label for="">Facebook</label>
          <input class= "input-field" type="text" id="facebook" value = "${getValiField(
            userDetail,
            "facebook"
          )}">
      </div>
      <div>
          <label for="roleId">RoleId</label>
          <select name="roleId" id="roleId">
              ${
                userDetail.roleId === "ADMIN"
                  ? '<option value = "ADMIN" selected >ADMIN</option> <option value = "USER" >USER</option>'
                  : '<option value = "USER" selected >USER</option> <option value = "ADMIN" >ADMIN</option>'
              }
          </select>
      </div>
      <div>
          <label for="">Address</label>
          <input class= "input-field" type="text" id="address" value="${getValiField(
            userDetail,
            "address"
          )}">
      </div>`;

    $("#input-container").append(inputFieldModal);
  });

  $("#closeModal").click(function (e) {
    e.preventDefault();
    $("#input-container").empty();
    $("#userModal").css("display", "none");
  });

  //thêm onchange vào input avatar
  $(document).on("change", "#avatar", function (e) {
    const file = $(this)[0].files[0].name;
    console.log(file);
    userDetailUpdate = { ...userDetail, avatar: file };
    userDetail = userDetailUpdate;
  });

  //thêm keyup vào input text
  $(document).on("change", ".input-field", function () {
    const key = $(this).attr("id");
    const value = $(this).val();
    userDetailUpdate = { ...userDetail, [key]: value };
    console.log(userDetailUpdate);
    userDetail = userDetailUpdate;
  });

  $(document).on("change", "#roleId", function () {
    const selectedValue = $(this).val();

    userDetailUpdate = { ...userDetail, roleId: selectedValue };
    userDetail = userDetailUpdate;
  });

  $("#btn-submit").click(function (e) {
    e.preventDefault();

    const formData = new FormData();

    // Đưa user object vào như 1 blob JSON
    formData.append(
      "user",
      new Blob([JSON.stringify(userDetail)], {
        type: "application/json",
      })
    );

    // Đưa file vào nếu có chọn
    const fileInput = document.getElementById("avatar");
    if (fileInput && fileInput.files.length > 0) {
      formData.append("avatar", fileInput.files[0]);
    }

    $.ajax({
      method: "POST",
      url: "http://localhost:8080/users/update",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      processData: false,
      contentType: false,
      data: formData,
      success: function (res) {
        console.log("Cập nhật thành công", res);
        location.reload();
      },
      error: function (xhr, status, error) {
        console.error(xhr.responseText, status, error);
      },
    });
  });

  $(document).on("click", ".deleteBtn", function () {
    const id = $(this).attr("id");
    console.log(id);
    $.ajax({
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      url: `http://localhost:8080/users/${id}`,
      success: function (res){
        location.reload();
      }
    })
  });
});
