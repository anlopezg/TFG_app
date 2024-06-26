import {useState} from 'react';
import {useDispatch} from 'react-redux';
import {Link} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';
import {useNavigate} from 'react-router-dom';

import {Errors} from '../../common';
import * as actions from '../actions';

const Login = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);
    let form;

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity()) {

            dispatch(actions.login(
                userName.trim(),
                password,
                () => navigate('/'),
                errors => setBackendErrors(errors),
                () => {
                    navigate('/users/login');
                    dispatch(actions.logout());
                }
            ));

        } else {
            setBackendErrors(null);
            form.classList.add('was-validated');
        }

    }

    return (
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="mt-4 mb-4 container d-flex justify-content-center align-items-center">
                <div className="card mb-3 card shopping-card min-width-card">

                    <div className="card-header coral">
                        <h2 className="retro ">
                            <FormattedMessage id="project.users.Login.title"/>
                        </h2>
                    </div>

                    <div className="card-body">
                        <form ref={node => form = node}
                            className="needs-validation" noValidate
                            onSubmit={e => handleSubmit(e)}>
                            <div className="form-group row">
                                <label htmlFor="userName" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.userName"/>
                                </label>
                                <div className="col-md-12">
                                    <input type="text" id="userName" className="form-control"
                                        value={userName}
                                        onChange={e => setUserName(e.target.value)}
                                        autoFocus
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="password" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.password"/>
                                </label>
                                <div className="col-md-12">
                                    <input type="password" id="password" className="form-control "
                                        value={password}
                                        onChange={e => setPassword(e.target.value)}
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row justify-content-center">
                                <div className="col-md-6 mt-5">
                                    <button type="submit" className="btn button-pink bold-label">
                                        <FormattedMessage id="project.global.buttons.submit"/>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div className="text-center">
                        <div>
                            <FormattedMessage id="project.users.SignUp.message"/>
                        </div>
                        <div className="mb-3">
                            <Link className="link-style" to="/users/signup">
                                <FormattedMessage id="project.users.SignUp.here"/>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

}

export default Login;
