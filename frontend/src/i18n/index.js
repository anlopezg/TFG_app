import messages from './messages';

export const initReactIntl = () => {

    let locale = (navigator.languages && navigator.languages[0]) ||
        navigator.language || navigator.userLanguage || 'en';

    console.log("Browser locale: ", locale);

    const localeWithoutRegionCode = locale.toLowerCase().split(/[_-]+/)[0];
    const localeMessages = messages[locale] || 
        messages[localeWithoutRegionCode] || messages['en'];

    if (locale === 'en') {
        return { locale: 'en', messages: messages['en'] };
    } else {

        const englishKeys = new Set(Object.keys(messages['en']));

        const untranslatedKeys = Array.from(englishKeys).filter(key => !localeMessages.hasOwnProperty(key));

        const mergedMessages = {...localeMessages};
        untranslatedKeys.forEach(key => {
            mergedMessages[key] = messages['en'][key];
        });

        return { locale, messages: mergedMessages };
    }

}

