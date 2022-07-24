const updateModal = document.getElementById('ModalEdit')
updateModal.addEventListener('show.bs.modal', event => {
    // Button that triggered the modal
    const button = event.relatedTarget
    // Extract info from data-bs-* attributes
    const userId = button.getAttribute('data-bs-userId')
    const userName = button.getAttribute('data-bs-userName')
    const userSurname = button.getAttribute('data-bs-userSurname')
    const userSex = button.getAttribute('data-bs-userSex')
    const userAge = button.getAttribute('data-bs-userAge')
    const userEmail = button.getAttribute('data-bs-userEmail')
    const userLogin = button.getAttribute('data-bs-userLogin')
    // Update the modal's content.


    const modaluserId = updateModal.querySelector('#userId')
    const modaluserName = updateModal.querySelector('#userName')
    const modaluserSurname = updateModal.querySelector('#userSurname')
    const modaluserSex = updateModal.querySelector("#userSex")
    const modaluserAge = updateModal.querySelector('#userAge')
    const modaluserEmail = updateModal.querySelector('#userEmail')
    const modaluserLogin = updateModal.querySelector('#userLogin')

    modaluserId.value = userId
    modaluserName.value = userName
    modaluserSurname.value = userSurname
    modaluserSex.value = userSex
    modaluserAge.value = userAge
    modaluserEmail.value = userEmail
    modaluserLogin.value = userLogin


})
