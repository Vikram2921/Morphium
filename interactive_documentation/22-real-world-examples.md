# Real-World Examples

Practical examples of Morphium DSL solving real-world data transformation challenges.

---

## E-Commerce Order Processing

### Example 1: Order Enrichment

```javascript
{
    orderId: $.id,
    customer: {
        id: $.customer.id,
        name: $.customer.firstName + " " + $.customer.lastName,
        email: lower($.customer.email),
        tier: $.customer.loyaltyTier
    },
    items: map($.items, "item", {
        productId: item.id,
        name: item.name,
        quantity: item.quantity,
        unitPrice: item.price,
        subtotal: item.price * item.quantity,
        discount: if ($.customer.loyaltyTier == "gold") { item.price * 0.2 }
                  else if ($.customer.loyaltyTier == "silver") { item.price * 0.1 }
                  else { 0 }
    }),
    pricing: {
        subtotal: sum(map($.items, "i", i.price * i.quantity)),
        tax: sum(map($.items, "i", i.price * i.quantity)) * 0.1,
        shipping: if (sum(map($.items, "i", i.price * i.quantity)) >= 100) { 0 } else { 9.99 },
        total: sum(map($.items, "i", i.price * i.quantity)) * 1.1 + 
               if (sum(map($.items, "i", i.price * i.quantity)) >= 100) { 0 } else { 9.99 }
    },
    status: "pending",
    createdAt: now()
}
```

---

## User Profile Transformation

### Example 2: Social Media Profile Aggregation

```javascript
{
    userId: $.id,
    profile: {
        displayName: exists($.nickname) ? $.nickname : $.firstName + " " + $.lastName,
        avatar: exists($.profileImage) ? $.profileImage : "default-avatar.png",
        bio: truncate($.bio, 160),
        location: $.location.city + ", " + $.location.country
    },
    stats: {
        followers: len($.followers),
        following: len($.following),
        posts: len($.posts),
        engagement: {
            totalLikes: sum(map($.posts, "p", p.likes)),
            totalComments: sum(map($.posts, "p", p.comments)),
            avgLikesPerPost: avg(map($.posts, "p", p.likes)),
            mostLikedPost: findFirst(
                sorted($.posts, "p", p.likes, "desc"),
                "p",
                true
            )
        }
    },
    activity: {
        lastPost: max(map($.posts, "p", p.timestamp)),
        isActive: max(map($.posts, "p", p.timestamp)) > (now() - 86400000),
        postFrequency: len($.posts) / 365
    }
}
```

---

## API Response Transformation

### Example 3: REST API Normalization

```javascript
{
    meta: {
        timestamp: now(),
        version: "v2",
        status: "success"
    },
    data: map($.results, "item", {
        id: item.id,
        type: item.type,
        attributes: {
            name: item.name,
            description: item.desc,
            createdAt: formatDate(item.created_timestamp, "yyyy-MM-dd"),
            updatedAt: formatDate(item.updated_timestamp, "yyyy-MM-dd")
        },
        relationships: {
            author: {
                id: item.author_id,
                link: "/api/users/" + toString(item.author_id)
            },
            categories: map(item.category_ids, "catId", {
                id: catId,
                link: "/api/categories/" + toString(catId)
            })
        }
    }),
    links: {
        self: "/api/items?page=" + toString($.page),
        next: if ($.page < $.totalPages) { "/api/items?page=" + toString($.page + 1) } else { null },
        prev: if ($.page > 1) { "/api/items?page=" + toString($.page - 1) } else { null }
    }
}
```

---

## Financial Data Processing

### Example 4: Transaction Analysis

```javascript
{
    accountId: $.accountId,
    period: {
        start: $.startDate,
        end: $.endDate
    },
    summary: {
        totalTransactions: len($.transactions),
        totalIncome: sum(map(filter($.transactions, "t", t.type == "credit"), "t", t.amount)),
        totalExpenses: sum(map(filter($.transactions, "t", t.type == "debit"), "t", t.amount)),
        netChange: sum(map(filter($.transactions, "t", t.type == "credit"), "t", t.amount)) -
                   sum(map(filter($.transactions, "t", t.type == "debit"), "t", t.amount)),
        largestTransaction: max(map($.transactions, "t", t.amount)),
        averageTransaction: avg(map($.transactions, "t", t.amount))
    },
    byCategory: groupBy($.transactions, "t", "category", {
        category: t.category,
        count: 1,
        total: t.amount
    }),
    topExpenses: limit(
        sorted(
            filter($.transactions, "t", t.type == "debit"),
            "t",
            t.amount,
            "desc"
        ),
        5
    ),
    alerts: [
        if (sum(map(filter($.transactions, "t", t.type == "debit"), "t", t.amount)) > 5000) {
            {type: "warning", message: "High spending detected"}
        } else { null },
        if (len(filter($.transactions, "t", t.amount > 1000)) > 10) {
            {type: "info", message: "Multiple large transactions"}
        } else { null }
    ] | filter("a", a != null)
}
```

---

## Log Processing

### Example 5: Application Log Aggregation

```javascript
{
    period: {
        start: min(map($.logs, "l", l.timestamp)),
        end: max(map($.logs, "l", l.timestamp)),
        duration: max(map($.logs, "l", l.timestamp)) - min(map($.logs, "l", l.timestamp))
    },
    stats: {
        total: len($.logs),
        byLevel: groupBy($.logs, "l", "level", {
            level: l.level,
            count: 1
        }),
        errors: len(filter($.logs, "l", l.level == "ERROR")),
        warnings: len(filter($.logs, "l", l.level == "WARN")),
        errorRate: len(filter($.logs, "l", l.level == "ERROR")) / len($.logs) * 100
    },
    topErrors: limit(
        sorted(
            groupBy(
                filter($.logs, "l", l.level == "ERROR"),
                "l",
                "message",
                {message: l.message, count: 1}
            ),
            "e",
            e.count,
            "desc"
        ),
        10
    ),
    timeline: groupBy($.logs, "l", formatDate(l.timestamp, "yyyy-MM-dd HH"), {
        hour: formatDate(l.timestamp, "yyyy-MM-dd HH"),
        count: 1,
        errors: if (l.level == "ERROR") { 1 } else { 0 }
    })
}
```

---

## Data Migration

### Example 6: Legacy System Migration

```javascript
{
    users: map($.legacy_users, "u", {
        id: uuid(),
        legacyId: u.user_id,
        profile: {
            firstName: u.first_name,
            lastName: u.last_name,
            email: lower(trim(u.email_address)),
            phone: replace(replace(u.phone_number, "-", ""), " ", "")
        },
        account: {
            username: lower(u.username),
            status: switch (u.status_code) {
                case 1: "active"
                case 2: "suspended"
                case 3: "deleted"
                default: "unknown"
            },
            createdAt: u.created_date,
            lastLogin: u.last_login_date
        },
        preferences: jsonParse(u.preferences_json),
        migrated: true,
        migratedAt: now()
    })
}
```

---

## IoT Sensor Data

### Example 7: Sensor Data Aggregation

```javascript
{
    deviceId: $.device.id,
    deviceName: $.device.name,
    location: $.device.location,
    timeRange: {
        start: min(map($.readings, "r", r.timestamp)),
        end: max(map($.readings, "r", r.timestamp))
    },
    statistics: {
        temperature: {
            current: $.readings[len($.readings) - 1].temperature,
            min: min(map($.readings, "r", r.temperature)),
            max: max(map($.readings, "r", r.temperature)),
            avg: avg(map($.readings, "r", r.temperature))
        },
        humidity: {
            current: $.readings[len($.readings) - 1].humidity,
            min: min(map($.readings, "r", r.humidity)),
            max: max(map($.readings, "r", r.humidity)),
            avg: avg(map($.readings, "r", r.humidity))
        },
        pressure: {
            current: $.readings[len($.readings) - 1].pressure,
            min: min(map($.readings, "r", r.pressure)),
            max: max(map($.readings, "r", r.pressure)),
            avg: avg(map($.readings, "r", r.pressure))
        }
    },
    alerts: [
        if ($.readings[len($.readings) - 1].temperature > 30) {
            {type: "warning", message: "High temperature detected"}
        } else { null },
        if ($.readings[len($.readings) - 1].humidity < 30) {
            {type: "warning", message: "Low humidity detected"}
        } else { null }
    ] | filter("a", a != null),
    trend: {
        temperatureTrend: if (avg(slice($.readings, len($.readings) - 5, len($.readings))) > 
                             avg(slice($.readings, 0, 5))) { "rising" } else { "falling" }
    }
}
```

---

## Analytics Dashboard

### Example 8: Business Metrics Dashboard

```javascript
{
    dashboard: {
        period: $.period,
        kpis: {
            revenue: {
                current: sum(map($.orders, "o", o.total)),
                previous: $.previousRevenue,
                growth: (sum(map($.orders, "o", o.total)) - $.previousRevenue) / $.previousRevenue * 100,
                target: $.targetRevenue,
                achievement: sum(map($.orders, "o", o.total)) / $.targetRevenue * 100
            },
            customers: {
                total: len(distinct(map($.orders, "o", o.customerId))),
                new: len(filter($.customers, "c", c.firstOrder == $.period)),
                returning: len(filter($.customers, "c", c.orderCount > 1)),
                churnRate: len(filter($.customers, "c", c.lastOrder < (now() - 7776000000))) / 
                          len($.customers) * 100
            },
            orders: {
                total: len($.orders),
                completed: len(filter($.orders, "o", o.status == "completed")),
                cancelled: len(filter($.orders, "o", o.status == "cancelled")),
                averageValue: avg(map($.orders, "o", o.total)),
                conversionRate: len(filter($.orders, "o", o.status == "completed")) / len($.orders) * 100
            }
        },
        trends: {
            dailyRevenue: groupBy($.orders, "o", formatDate(o.createdAt, "yyyy-MM-dd"), {
                date: formatDate(o.createdAt, "yyyy-MM-dd"),
                revenue: o.total,
                orders: 1
            }),
            topProducts: limit(
                sorted(
                    groupBy(
                        flatMap($.orders, "o", o.items),
                        "item",
                        "productId",
                        {productId: item.productId, name: item.name, revenue: item.total, quantity: item.quantity}
                    ),
                    "p",
                    p.revenue,
                    "desc"
                ),
                10
            ),
            customerSegments: groupBy($.customers, "c", c.segment, {
                segment: c.segment,
                count: 1,
                revenue: c.totalSpent
            })
        }
    }
}
```

---

## Related Topics

- [Common Patterns](23-common-patterns.md) - Reusable patterns
- [Performance Tips](18-performance.md) - Optimize transformations
- [Functions](README.md#functions) - All available functions

---

[← Back to Documentation](README.md)
