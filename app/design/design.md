```mermaid
graph TD
    A[App Entry] --> B[Login Screen]
    B --> |Succes| C[Home Screen]
    B --> |Failed login| B
    C <--> D[Profile Screen]
    C <--> E[Settings Screen]
    C <--> F[About Us Screen]
    C <--> G[Terms & Conditions Screen]
    C <--> H[Programmed Trips]
    C <--> I[Plan New Trip]
    C <--> J[Explore]
    D <--> K[User Preferences]
    H <--> L[Your Trip Details]

classDiagram
    class User {
        +String userId
        +String name
        +String email
        +Sring password
    }

    class Authentication {
        +void login(email, password)
        +void logout()
        +void resetPassword(email)
    }

    class Preferences {
        +String userId
        +Boolean notificationsEnabled
        +String preferredLanguage
        +String theme 
        +void updatePreferences()
    }

    class AIRecommendations {
        +void getPersonalizedRecommendations()
    }

    class Trip {
        +String tripId
        +String userId
        +Date startDate
        +Date endDate
        +String destination
        +void addItineraryItem()
        +void removeItineraryItem()
    }

