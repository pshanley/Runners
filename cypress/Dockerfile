FROM cypress/included:4.1.0

WORKDIR /app

COPY ./cypress.json ./cypress.json
COPY ./cypress.env.json ./cypress.env.json
COPY ./instagram.js ./cypress/integration/instagram.js

RUN npx cypress run

