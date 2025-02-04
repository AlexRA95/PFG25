document.getElementById('searchHeader').addEventListener('input', (event)=>{
    if (event.target.value.length >= 3){
        document.getElementById('searchBtnHeader').disabled = false;
    }
});

//Valida formulario de registro y de cambio de datos generales
document.addEventListener('DOMContentLoaded', function () {
    // Validación del formulario de registro
    (function validarRegistro() {
        const form = document.querySelector('#registroModal form');
        if (!form) return; // Asegúrate de que el formulario exista en el DOM.
        const inputs = form.querySelectorAll('input');
        const submitButton = form.querySelector('button[type="submit"]');

        inputs.forEach(input => {
            input.addEventListener('blur', function () {
                validarInput(input);
                checkFormulario();
            });
        });

        async function validarInput(input) {
            const value = input.value;
            const id = input.id;
            let todoBien = true;
            let mensaje = '';

            switch (id) {
                case 'floatingEmail':
                    todoBien = validarEmail(value);
                    if (todoBien) {
                        const data = await correoEnUso();
                        todoBien = !data.correoEnUso;
                        if (!todoBien) {
                            mensaje = 'El correo electrónico ya está en uso.';
                        }
                    } else {
                        mensaje = 'El correo electrónico no es válido.';
                    }
                    break;
                case 'floatingPassword':
                    todoBien = value.length >= 8 && value.length <= 100;
                    if (!todoBien) {
                        mensaje = 'La contraseña debe tener entre 8 y 100 caracteres.';
                    }
                    break;
                case 'floatingPasswordConf':
                    const password = document.querySelector('#floatingPassword').value;
                    todoBien = value === password;
                    if (!todoBien) {
                        mensaje = 'Las contraseñas no coinciden.';
                    }
                    break;
                case 'floatingName':
                    todoBien = value.length > 0 && value.length <= 20;
                    if (!todoBien) {
                        mensaje = 'El nombre no puede estar vacío y debe tener menos de 20 caracteres.';
                    }
                    break;
                case 'floatingSurname':
                    todoBien = value.length > 0 && value.length <= 30;
                    if (!todoBien) {
                        mensaje = 'Los apellidos no pueden estar vacíos y deben tener menos de 30 caracteres.';
                    }
                    break;
                case 'floatingNIF':
                    if (/^[0-9]{8}$/.test(value)) {
                        const data = await calcularLetraNIF(document.getElementById('floatingNIF').value);
                        if (data !== null) {
                            document.getElementById('floatingNIF').value += data.Letra;
                            todoBien = /^[0-9]{8}[A-Z]$/.test(document.getElementById('floatingNIF').value);
                        } else {
                            todoBien = false;
                        }
                    } else {
                        todoBien = false;
                    }
                    if (!todoBien) {
                        mensaje = 'El NIF no es válido.';
                    }
                    break;
                case 'floatingPhone':
                    todoBien = !value || /^[0-9]{9}$/.test(value);
                    if (!todoBien) {
                        mensaje = 'El número de teléfono no es válido.';
                    }
                    break;
                case 'floatingDireccion':
                    todoBien = value.length > 0 && value.length <= 40;
                    if (!todoBien) {
                        mensaje = 'La dirección no puede estar vacía y debe tener menos de 40 caracteres.';
                    }
                    break;
                case 'floatingcdPostal':
                    todoBien = /^(0[1-9][0-9]{3}|[1-4][0-9]{4}|5[0-2][0-9]{3})$/.test(value);
                    if (!todoBien) {
                        mensaje = 'El código postal no es válido.';
                    }
                    break;
                case 'floatingLocalidad':
                    todoBien = value.length > 0 && value.length <= 40;
                    if (!todoBien) {
                        mensaje = 'La localidad no puede estar vacía y debe tener menos de 40 caracteres.';
                    }
                    break;
                case 'floatingProvincia':
                    todoBien = value.length > 0 && value.length <= 30;
                    if (!todoBien) {
                        mensaje = 'La provincia no puede estar vacía y debe tener menos de 30 caracteres.';
                    }
                    break;
            }

            if (todoBien) {
                input.classList.remove('is-invalid');
                input.classList.add('is-valid');
            } else {
                input.classList.remove('is-valid');
                input.classList.add('is-invalid');
                showAlert(mensaje);
            }
        }

        function checkFormulario() {
            const allValid = Array.from(inputs).every(input => input.classList.contains('is-valid'));
            submitButton.disabled = !allValid;
        }

        function showAlert(message, type = 'danger') {
            const alertContainer = document.createElement('div');
            alertContainer.className = `alert alert-${type} alert-dismissible fade show position-fixed top-0 end-0 m-3`;
            alertContainer.role = 'alert';
            alertContainer.style.zIndex = '1050';
            alertContainer.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;
            document.body.appendChild(alertContainer);

            setTimeout(() => {
                alertContainer.classList.remove('show');
                alertContainer.classList.add('fade');
                setTimeout(() => alertContainer.remove(), 500);
            }, 3000);
        }

        function validarEmail(email) {
            const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return re.test(email);
        }


    })();

    // Validación del formulario de cambio de datos generales
    (function validarDatosGenerales() {
        const form = document.querySelector('form[action$="CambiarDatosGenerales"]');
        if (!form) return; // Ensure the form exists in the DOM.
        const inputs = form.querySelectorAll('input');
        const submitButton = form.querySelector('button[type="submit"]');

        submitButton.addEventListener('click', function (event) {
            let formValid = true;
            inputs.forEach(input => {
                validarInput(input);
                if (!input.classList.contains('is-valid')) {
                    formValid = false;
                }
            });
            if (!formValid) {
                event.preventDefault();
                showAlert('Por favor, corrige los errores en el formulario.');
            }
        });

        async function validarInput(input) {
            const value = input.value;
            const id = input.id;
            let todoBien = true;

            switch (id) {
                case 'nombre':
                    todoBien = value.length > 0 && value.length <= 20;
                    break;
                case 'apellidos':
                    todoBien = value.length > 0 && value.length <= 30;
                    break;
                case 'telefono':
                    todoBien = !value || /^[0-9]{9}$/.test(value);
                    break;
                case 'direccion':
                    todoBien = value.length > 0 && value.length <= 40;
                    break;
                case 'codigoPostal':
                    todoBien = /^(0[1-9][0-9]{3}|[1-4][0-9]{4}|5[0-2][0-9]{3})$/.test(value);
                    break;
                case 'localidad':
                    todoBien = value.length > 0 && value.length <= 40;
                    break;
                case 'provincia':
                    todoBien = value.length > 0 && value.length <= 30;
                    break;
            }

            if (todoBien) {
                input.classList.remove('is-invalid');
                input.classList.add('is-valid');
            } else {
                input.classList.remove('is-valid');
                input.classList.add('is-invalid');
                showAlert(mensajeError(id));
            }
        }

        function showAlert(message, type = 'danger') {
            const alertContainer = document.createElement('div');
            alertContainer.className = `alert alert-${type} alert-dismissible fade show position-fixed top-0 end-0 m-3`;
            alertContainer.role = 'alert';
            alertContainer.style.zIndex = '1050';
            alertContainer.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;
            document.body.appendChild(alertContainer);

            setTimeout(() => {
                alertContainer.classList.remove('show');
                alertContainer.classList.add('fade');
                setTimeout(() => alertContainer.remove(), 500);
            }, 3000);
        }

        function mensajeError(id) {
            switch (id) {
                case 'nombre':
                    return 'El nombre no puede estar vacío y debe tener menos de 20 caracteres.';
                case 'apellidos':
                    return 'Los apellidos no pueden estar vacíos y deben tener menos de 30 caracteres.';
                case 'nif':
                    return 'El NIF no es válido.';
                case 'telefono':
                    return 'El número de teléfono no es válido.';
                case 'direccion':
                    return 'La dirección no puede estar vacía y debe tener menos de 40 caracteres.';
                case 'codigoPostal':
                    return 'El código postal no es válido.';
                case 'localidad':
                    return 'La localidad no puede estar vacía y debe tener menos de 40 caracteres.';
                case 'provincia':
                    return 'La provincia no puede estar vacía y debe tener menos de 30 caracteres.';
                default:
                    return 'Este campo no es válido.';
            }
        }
    })();

    //Validacion de contraseñas
    (function validatePasswordForm() {
        const form = document.querySelector('form[action$="CambiarContrasenia"]');
        if (!form) return;
        const currentPasswordInput = form.querySelector('#contraseniaActual');
        const newPasswordInput = form.querySelector('#nuevaContrasenia');
        const confirmPasswordInput = form.querySelector('#ConfNuevaContrasenia');
        const submitButton = form.querySelector('button[type="submit"]');

        function validatePasswords() {
            const currentPassword = currentPasswordInput.value;
            const newPassword = newPasswordInput.value;
            const confirmPassword = confirmPasswordInput.value;

            let allValid = true;

            if (currentPassword.length <= 7) {
                currentPasswordInput.classList.remove('is-valid');
                currentPasswordInput.classList.add('is-invalid');
                showAlert('La contraseña actual debe tener más de 8 caracteres.');
                allValid = false;
            } else {
                currentPasswordInput.classList.remove('is-invalid');
                currentPasswordInput.classList.add('is-valid');
            }

            if (newPassword.length <= 7) {
                newPasswordInput.classList.remove('is-valid');
                newPasswordInput.classList.add('is-invalid');
                showAlert('La nueva contraseña debe tener más de 8 caracteres.');
                allValid = false;
            } else {
                newPasswordInput.classList.remove('is-invalid');
                newPasswordInput.classList.add('is-valid');
            }

            if (confirmPassword !== newPassword) {
                confirmPasswordInput.classList.remove('is-valid');
                confirmPasswordInput.classList.add('is-invalid');
                showAlert('Las contraseñas no coinciden.');
                allValid = false;
            } else {
                confirmPasswordInput.classList.remove('is-invalid');
                confirmPasswordInput.classList.add('is-valid');
            }

            submitButton.disabled = !allValid;
        }

        currentPasswordInput.addEventListener('blur', validatePasswords);
        newPasswordInput.addEventListener('blur', validatePasswords);
        confirmPasswordInput.addEventListener('blur', validatePasswords);

        function showAlert(message, type = 'danger') {
            const alertContainer = document.createElement('div');
            alertContainer.className = `alert alert-${type} alert-dismissible fade show position-fixed top-0 end-0 m-3`;
            alertContainer.role = 'alert';
            alertContainer.style.zIndex = '1050';
            alertContainer.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;
            document.body.appendChild(alertContainer);

            setTimeout(() => {
                alertContainer.classList.remove('show');
                alertContainer.classList.add('fade');
                setTimeout(() => alertContainer.remove(), 500);
            }, 3000);
        }
    })();
});

if (document.getElementById('precio') != null){
    document.getElementById('precio').addEventListener('input', (event)=>{
        const value = event.target.value;
        const formattedValue = new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' }).format(value);
        document.getElementById('precioValue').textContent = formattedValue;
    });
}

//Función para generar la letra del NIF y mostrarla en el campo correspondiente
async function calcularLetraNIF(nif) {
    const URL = "CalcularLetraNIF_AJAX";

        const myHeaders = {
            "Content-Type": "application/x-www-form-urlencoded",
        };

        try {
            const response = await fetch(URL, {
                method: "POST",
                body: JSON.stringify({
                    NIF: nif
                }),
                headers: myHeaders,
            });
            if (!response.ok) {
                throw new Error('Error en la solicitud: ' + response.statusText);
            }
            const data = await response.json();
            return data;


        } catch (error) {
            console.error('Error al obtener el mensaje del servidor:', error);
        }
}

//Función para comprobar si el correo electronico que el usuario quiere usar ya está en uso
async function correoEnUso() {
    const URL = "CorreoEnUsoAJAX";
    const email = document.getElementById("floatingEmail").value;
    const myHeaders = {
        "Content-Type": "application/x-www-form-urlencoded",
    };

    try {
        const response = await fetch(URL, {
            method: "POST",
            body: JSON.stringify({
                EMAIL: email
            }),
            headers: myHeaders,
        });
        if (!response.ok) {
            throw new Error('Error en la solicitud: ' + response.statusText);
        }
        const data = await response.json();
        return data;


    } catch (error) {
        console.error('Error al obtener el mensaje del servidor:', error);
    }
}