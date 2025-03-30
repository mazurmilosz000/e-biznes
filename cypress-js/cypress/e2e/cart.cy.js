describe('Cart Page Tests', () => {
    beforeEach(() => {
      cy.visit('http://localhost:3000/cart');
    });

    it('Should have the correct structure', () => {
        cy.get('h2').should('have.text', 'Cart');
        cy.get('p').should('have.text', 'Cart is empty');
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

      it('Should add a product to the cart and update the UI', () => {
        cy.intercept('GET', '/products', { fixture: 'products.json' }).as('getProducts');
        
        cy.visit('http://localhost:3000/');
        
        cy.wait('@getProducts');
        
        cy.get('button').contains('Add to Cart').should('exist').and('be.visible');
        
        cy.get('button').contains('Add to Cart').first().trigger("click");
        
        cy.wait(500);
      
        cy.get('nav a[href="/cart"]').click();
      
        cy.get('p').should('not.exist');
      
        cy.get('ul li').should('have.length.greaterThan', 0);
      
        cy.get('ul li').first().should('contain.text', 'Laptop - $1200 x 1');
        cy.get('button').contains('Remove').should('exist').and('be.visible');
        cy.get('button').contains('Clear Cart').should('exist').and('be.visible');

        cy.get('button').contains('Remove').first().trigger("click");
        cy.get('p').should('have.text', 'Cart is empty');

        cy.get('nav a[href="/"]').click();
        cy.get('button').contains('Add to Cart').first().trigger("click");
        cy.get('button').contains('Add to Cart').first().trigger("click");
        cy.get('button').contains('Add to Cart').first().trigger("click");
        cy.get('nav a[href="/cart"]').click();
        cy.get('p').should('not.exist');
        cy.get('ul li').first().should('contain.text', 'Laptop - $1200 x 3');
        cy.get('button').contains('Remove').first().trigger("click");
        cy.get('ul li').first().should('contain.text', 'Laptop - $1200 x 2');
        cy.get('button').contains('Clear Cart').first().trigger("click");
        cy.get('p').should('exist');
        cy.get('p').should('have.text', 'Cart is empty');
      });

  });