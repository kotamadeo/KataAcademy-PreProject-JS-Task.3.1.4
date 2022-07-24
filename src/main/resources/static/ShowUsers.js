async function showUsers() {


    const response = await fetch("api/users");

    if (response.ok) {
        let json = await response.json()
            .then(data => replaceTable(data));
    } else {
        alert("Ошибка HTTP: " + response.status);
    }

    function replaceTable(data) {

        const placement = document.getElementById("usersTablePlacement")
        placement.innerHTML = "";
        data.forEach(({id, surname, name, sex, email, age, username, roles}) => {
            let userRoles = "";
            roles.forEach((role) => {
                userRoles = userRoles + role.roleTitle.split("_")[1] + " ";
            })
            const element = document.createElement("tr");
            element.innerHTML = `
            <th scope="row">${id}</th>
            <td>${name}</td>
            <td>${surname}</td>
            <td>${sex}</td>
            <td>${email}</td>
            <td>${age}</td>
            <td>${username}</td>
            <td>${userRoles}</td>
            <td>
                 <button type="button" class="btn btn-primary text-light" data-bs-userId=${id}
                 data-bs-userName=${name} data-bs-userSurname=${surname} data-bs-userAge=${age}
                 data-bs-userEmail=${email} data-bs-userSex=${sex} data-bs-userLogin=${username}
                 data-bs-toggle="modal" data-bs-target="#ModalEdit">Изменить?</button>
                 </td>

            <td>
                <button type="button" class="btn btn-danger text-light" data-bs-userId=${id}
                    data-bs-userName=${name} data-bs-userSurname=${surname} data-bs-userAge=${age}
                    data-bs-userEmail=${email} data-bs-toggle="modal"
                    data-bs-target="#ModalDelete">Удалить?</button>
            </td>            
            `
            placement.append(element);
        })
    }
}
