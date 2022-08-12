$(async function() {
    await newUser();
});
async function newUser() {
    await fetch("http://localhost:8080/api/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let el = document.createElement("option");
                el.text = role.name.substring(5);
                el.value = role.id;
                $('#createUserRoles')[0].appendChild(el);
            })
        })

    const form = document.forms["formNewUser"];

    form.addEventListener('submit', addNewUser)

    function addNewUser(e) {
        e.preventDefault();
        let newUserRoles = [];
        for (let i = 0; i < form.roles.options.length; i++) {
            if (form.roles.options[i].selected) newUserRoles.push({
                id : form.roles.options[i].value,
                name : form.roles.options[i].name
            })
        }
        fetch("http://localhost:8080/api/users", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: form.name.value,
                surname: form.surname.value,
                sex: form.sex.value,
                email: form.email.value,
                age: form.age.value,
                nickname: form.nickname.value,
                password: form.password.value,
                roles: newUserRoles
            })
        }).then(() => {
            form.reset();
            allUsers();
            $('#allUsersTable').click();
        })
    }

}
$(async function() {
    await allUsers();
});
const table = $('#tbodyAllUserTable');
async function allUsers() {
    table.empty()
    fetch("http://localhost:8080/api/users")
        .then(res => res.json())
        .then(data => {
            data.forEach(user => {
                let tableWithUsers = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>   
                            <td>${user.sex}</td>   
                            <td>${user.email}</td>
                            <td>${user.age}</td>       
                            <td>${user.nickname}</td>                
                            <td>${user.roles.map(role => " " + role.name.substring(5))}</td>
                            <td>
                                <button type="button" class="btn btn-primary text-light" data-toggle="modal" id="buttonEdit"
                                data-action="edit" data-id="${user.id}" data-target="#edit">Изменить?</button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-danger text-light" data-toggle="modal" id="buttonDelete"
                                data-action="delete" data-id="${user.id}" data-target="#delete">Удалить?</button>
                            </td>
                        </tr>)`;
                table.append(tableWithUsers);
            })
        })
}
$('#delete').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showDeleteModal(id);
})

async function showDeleteModal(id) {
    let user = await getUser(id);
    let form = document.forms["formDeleteUser"];
    form.id.value = user.id;
    form.name.value = user.name;
    form.surname.value = user.surname;
    form.sex.value = user.sex;
    form.age.value = user.age;
    form.email.value = user.email;
    form.nickname.value = user.nickname;


    $('#deleteUserRoles').empty();

    await fetch("http://localhost:8080/api/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < user.roles.length; i++) {
                    if (user.roles[i].name === role.name) {
                        selectedRole = true;
                        break;
                    }
                }
                let el = document.createElement("option");
                el.text = role.name.substring(5);
                el.value = role.id;
                if (selectedRole) el.selected = true;
                $('#deleteUserRoles')[0].appendChild(el);
            })
        });
}
async function getUser(id) {
    let url = "http://localhost:8080/api/users/" + id;
    let response = await fetch(url);
    return await response.json();
}
$(async function() {

    deleteUser();
});
function deleteUser(){
    const deleteForm = document.forms["formDeleteUser"];
    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch("http://localhost:8080/api/users/" + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                $('#deleteFormCloseButton').click();
                allUsers();
            })
    })
}
$('#edit').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showEditModal(id);
})

async function showEditModal(id) {
    let user = await getUser(id);
    let form = document.forms["formEditUser"];
    form.id.value = user.id;
    form.name.value = user.name;
    form.surname.value = user.surname;
    form.sex.value = user.sex;
    form.age.value = user.age;
    form.email.value = user.email;
    form.nickname.value = user.nickname;
    form.password.value = user.password;

    $('#updateUserRoles').empty();

    await fetch("http://localhost:8080/api/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < user.roles.length; i++) {
                    if (user.roles[i].name === role.name) {
                        selectedRole = true;
                        break;
                    }
                }
                let el = document.createElement("option");
                el.text = role.name.substring(5);
                el.value = role.id;
                if (selectedRole) el.selected = true;
                $('#updateUserRoles')[0].appendChild(el);
            })
        })
}

$(async function() {
    editUser();

});
function editUser() {
    const editForm = document.forms["formEditUser"];
    editForm.addEventListener("submit", ev => {
        ev.preventDefault();
        let editUserRoles = [];
        for (let i = 0; i < editForm.roles.options.length; i++) {
            if (editForm.roles.options[i].selected) editUserRoles.push({
                id : editForm.roles.options[i].value,
                name : "ROLE_" + editForm.roles.options[i].text
            })
        }

        fetch("http://localhost:8080/api/users/" + editForm.id.value, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editForm.id.value,
                name: editForm.name.value,
                surname: editForm.surname.value,
                sex: editForm.sex.value,
                age: editForm.age.value,
                email: editForm.email.value,
                nickname: editForm.nickname.value,
                password: editForm.password.value,
                roles: editUserRoles
            })
        }).then(() => {
            $('#editFormCloseButton').click();
            allUsers();
        })
    })
}$(async function() {
    await thisUser();
});
async function thisUser() {
    fetch("http://localhost:8080/api/user")
        .then(res => res.json())
        .then(data => {
            // Добавляем информацию в шапку
            $('#headerUsername').append(data.email);
            let roles = data.roles.map(role => " " + role.name.substring(5));
            $('#headerRoles').append(roles);

            //Добавляем информацию в таблицу
            let user = `$(
            <tr>
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.surname}</td>
                <td>${data.sex}</td>
                <td>${data.email}</td>
                <td>${data.age}</td>
                <td>${data.nickname}</td>
                <td>${roles}</td>)`;
            $('#userPanelBody').append(user);
        })
}



