import {FormattedMessage} from "react-intl";
import {useEffect} from "react";
import * as actions from "../../actions.js";
import {useDispatch, useSelector} from "react-redux";
import * as selectors from "../../selectors.js";

const GetStripeAccount = () =>{

    const stripeAccount = useSelector(selectors.getStripeAccount);

    const getValue = (charges) =>{
        if(charges === true){
            return <FormattedMessage id="project.users.StripeAccount.charges.true"/>
        }else{
            return <FormattedMessage id="project.users.StripeAccount.charges.false"/>
        }
    }


    return (
        <div>
            {stripeAccount ? (
                <div className="mt-4 mb-4 container d-flex justify-content-center">
                    <div className="card shopping-card min-width-card">

                        <div className="card-header coral ">
                            <h2 className="retro">
                                <FormattedMessage id="project.users.StripeAccount.title"/>
                            </h2>
                        </div>

                        <div className="card-body">



                            <div className="form-group ">
                                <label className="col-form-label bold-label">
                                    <FormattedMessage id="project.users.StripeAccount.charges"/>
                                </label>
                                <p className="text-muted ">
                                    <FormattedMessage id="project.users.StripeAccount.charges.descr"/>
                                </p>
                                <div className="form-control-plaintext border rounded p-2">
                                    {getValue(stripeAccount.chargesEnabled)}
                                </div>
                            </div>

                            <div className="form-group ">
                                <label className="col-form-label bold-label">
                                    <FormattedMessage id="project.users.StripeAccount.payouts"/>
                                </label>
                                <p className="text-muted ">
                                    <FormattedMessage id="project.users.StripeAccount.payouts.descr"/>
                                </p>
                                <div className="form-control-plaintext border rounded p-2">
                                    {getValue(stripeAccount.payoutsEnabled)}
                                </div>
                            </div>

                            <div className="row">

                                <div className="col-md-6">
                                    <div className="form-group mt-3">
                                        <label className="col-form-label bold-label">
                                            <FormattedMessage id="project.users.StripeAccount.country"/>
                                        </label>
                                        <div className="form-control-plaintext border rounded p-2">
                                            {stripeAccount.country}
                                        </div>
                                    </div>
                                </div>
                                <div className="col-md-6">
                                    <div className="form-group mt-3">
                                        <label className="col-form-label bold-label">
                                            <FormattedMessage id="project.users.StripeAccount.currency" />
                                        </label>
                                        <div className="form-control-plaintext border rounded p-2">
                                            {stripeAccount.defaultCurrency}
                                        </div>
                                    </div>
                                </div>
                            </div>


                        </div>

                    </div>

                </div>
            ):(
                <div>
                    <FormattedMessage id="project.users.StripeAccount.notFound"/>
                </div>
            )}

        </div>



    )
}

export default GetStripeAccount;