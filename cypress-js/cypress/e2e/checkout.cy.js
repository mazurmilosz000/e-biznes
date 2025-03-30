describe('Checkout Page Tests', () => {
    beforeEach(() => {
      cy.visit('http://localhost:3000/checkout');
    });

    it('Should have the correct structure', () => {
        cy.get('h2').should('have.text', 'Checkout');
        cy.get('button').should('have.text', 'Pay Now');
        cy.get('button').should('be.visible');
        cy.get('button').should('be.disabled');
    })

    it('Should be able to pay', () => {

        cy.get('nav a[href="/"]').click();
        cy.get('button').contains('Add to Cart').first().trigger("click");
        cy.get('nav a[href="/checkout"]').click();
        cy.get('button').should('be.visible');
        cy.get('button').should('be.enabled');
        
        const stub = cy.stub()  
        cy.on ('window:alert', stub)
        cy
        .get('button').contains('Pay Now').click()
        .then(() => {
          expect(stub.getCall(0)).to.be.calledWith('Order placed successfully!')      
        })  
        cy.get('button').should('have.prop', 'disabled', true);
    })

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