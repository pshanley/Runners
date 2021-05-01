it('Test Case 1', function() {

    const waitTime = 4000
    cy.visit('https://www.instagram.com/accounts/login/')
    cy.get('input[name="username"]').type(Cypress.env('INSTAGRAM_USERNAME')) // read credentials from cypress.env.json
    cy.get('input[name="password"]').type(Cypress.env('INSTAGRAM_PASSWORD'))
    cy.wait(1000)
    cy.get('button[type=submit]').click()

    cy.wait(waitTime)

    cy.getCookie('sessionid').then(($cook) => {
        let cookieValue = $cook.value
        cy.log(cookieValue)
        cy.writeFile('/app/cypress/cookies/sessionid.json',{sessionid: cookieValue })
    })

    cy.getCookie('csrftoken').then(($cook) => {
        let cookieValue = $cook.value
        cy.log(cookieValue) 
        cy.writeFile('/app/cypress/cookies/csrf.json',{csrftoken: cookieValue })
    })
    
})
