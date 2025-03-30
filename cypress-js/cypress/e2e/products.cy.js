describe('Products Page Tests', () => {
    beforeEach(() => {
        cy.intercept('GET', 'http://localhost:8080/products', { fixture: 'products.json' }).as('getProducts');
      cy.visit('http://localhost:3000/');
    });

    it('Should have the correct structure', () => {
        cy.wait('@getProducts');

        cy.get('h2').should('have.text', 'Products');
        cy.get('span').contains("Laptop - $1200").should('be.visible');
        cy.get('span').contains("Smartphone - $800").should('be.visible');
        cy.get('span').contains("Tablet - $900").should('be.visible');
    })

    it('Should display 3 buttons with "Add to Cart" text', () => {
        cy.get('button')
          .should('have.length', 3)
          .each(($btn) => {
            cy.wrap($btn).should('have.text', 'Add to Cart');
          });
      });

      it('Should have a navigation bar with correct links', () => {
        cy.get('nav').within(() => {
          cy.get('a').should('have.length', 3);
      
          cy.get('a').eq(0)
            .should('have.attr', 'href', '/')
            .and('have.text', 'Products')
            .and('be.visible')
      
          cy.get('a').eq(1)
            .should('have.attr', 'href', '/cart')
            .and('have.text', 'Cart')
            .and('be.visible')
      
          cy.get('a').eq(2)
            .should('have.attr', 'href', '/checkout')
            .and('have.text', 'Checkout')
            .and('be.visible')
        });
      
        cy.get('nav').should('exist')
          .and('be.visible')
          .and('contain.html', '<a href="/"')
          .and('contain.html', '<a href="/cart"')
          .and('contain.html', '<a href="/checkout"');
      });
  });