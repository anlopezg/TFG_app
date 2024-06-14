import React from 'react';
import {Form} from "react-router-dom";
import {FormattedMessage} from "react-intl";

const crochetAbbreviationsUS = [

    // Crochet English
    { short: 'sl kt', full: 'Slip knot' },
    { short: 'ch', full: 'Chain' },
    { short: 'sl st', full: 'Slip stitch' },
    { short: 'sc', full: 'Single crochet' },
    { short: 'dc', full: 'Double crochet' },
    { short: 'sc2tog', full: 'Single crochet two together' },
];

const crochetAbbreviationsES = [
    { short: 'pb', full: 'Punto bajo' },
    { short: 'pc', full: 'Punto cadeneta' },
    { short: 'pr', full: 'Punto raso' },
    { short: 'pa', full: 'Punto alto' },
    { short: 'dpa', full: 'Doble punto alto' },
    { short: 'pbj', full: 'Punto bajo junto' },
];

const knitAbbreviationsUS = [
    { short: 'k', full: 'Knit' },
    { short: 'p', full: 'Purl' },
];

const knitAbbreviationsES = [
    { short: 'd', full: 'Derecho' },
    { short: 'r', full: 'Revés' },
];



const Abbreviations = ({ craft, standard }) => {
    let abbreviations = [];

    if (standard === 'CUSTOM') {
        return <FormattedMessage id="project.products.Pattern.standard.custom"/>;
    }


    if (craft === 1) {
        abbreviations = standard === 'ES' ? crochetAbbreviationsES : crochetAbbreviationsUS;
    } else if (craft === 2) {
        abbreviations = standard === 'ES' ? knitAbbreviationsES : knitAbbreviationsUS;
    }


    // If the standard is not recognized, use US abbreviations
    if (!abbreviations.length) {
        abbreviations = craft === 'crochet' ? crochetAbbreviationsUS : knitAbbreviationsUS;
    }

    return (
        <ul className="list-unstyled">
            {abbreviations.map((abbr, index) => (
                <li key={index}><strong>{abbr.short}</strong>: {abbr.full}</li>
            ))}
        </ul>
    );
};

export default Abbreviations;
