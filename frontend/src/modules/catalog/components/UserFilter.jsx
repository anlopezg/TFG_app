import {useDispatch} from "react-redux";
import * as actions from '../actions';
import * as userActions from '../../users/actions.js';
import {useState} from "react";
import {FormattedMessage} from "react-intl";
import { useNavigate} from "react-router-dom";

const UserFilter = () =>{

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [username, setUsername] = useState('');

    const handleSubmit = event =>{
        event.preventDefault();

        dispatch(actions.clearUserSearch());
        dispatch(actions.clearProductSearch());
        dispatch(userActions.clearFoundUser());

        dispatch(actions.findUsers({
            username: username.trim(),
            page:0}));

        //dispatch(actions.clearUserSearch());
        setUsername('');

        navigate('/catalog/find-users-result');


    }

    return (
        <div className="container">

            <h4 className="text-left m-3 mb-4 bold font-bold text-uppercase">
                <i className="fa-solid fa-magnifying-glass m-2 "></i>
                <FormattedMessage id="project.catalog.Filter.Users"/>
            </h4>

            <form className="form-wrapper " onSubmit={e => handleSubmit(e)}>
                <div className="row input-group rounded justify-content-center align-items-end">

                    <div className="col-md-5">
                        <label htmlFor="username">
                            <FormattedMessage id="project.catalog.Search.ByUsername"/>
                        </label>
                        <input id="username" type="search" className="form-control rounded"
                               aria-label="Search" aria-describedby="search-addon"
                               value={username} onChange={e=> setUsername(e.target.value)}/>
                    </div>

                    <div className="col-md-3">
                        <div className="form-group">
                            <button type="submit" className="btn button-coral">
                                <FormattedMessage id="project.global.buttons.search"/>
                            </button>
                        </div>
                    </div>

                </div>

            </form>


        </div>
    )

}

export default UserFilter;